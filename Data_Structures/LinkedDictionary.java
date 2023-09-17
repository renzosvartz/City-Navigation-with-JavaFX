/**
 * @author Renzo Svartz
 */

package Data_Structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import Interfaces.DictionaryInterface;
import JavaFX.EntryNode;

/**
 * Class representing a linked dictionary
 * @param <K> generic key
 * @param <V> generic value
 */
public class LinkedDictionary<K extends Comparable<? super K>, V> implements DictionaryInterface<K, V>
{
	private EntryNode<K, V> firstNode;
	private EntryNode<K, V> tailNode;
	private int numOfEntries;
	
	/**
	 * Default constructor
	 */
	public LinkedDictionary()
	{
		firstNode = null;
		tailNode = null;
		numOfEntries = 0;
	}
	
	/**
	 * Copy constructor
	 * @param dictionary dictionary to copy
	 */
	public LinkedDictionary(DictionaryInterface<K, V> dictionary)
	{
		firstNode = null;
		tailNode = null;
		numOfEntries = 0;
		
		Iterator<K> keyIterator = dictionary.getKeyIterator();
		
		while (keyIterator.hasNext())
		{
			K key = keyIterator.next();
			add(key, dictionary.getValue(key));
		}
	}
	
	/** Adds a new entry to this dictionary. If the given search key already exists in the dictionary, replaces the corresponding value. 
	 *  @param key An object search key of the new entry. 
	 *  @param value An object associated with the search key. 
	 *  @return Either null if the new entry was added to the dictionary or the value that was associated with key if that value was replaced.
	 */
	public V add(K key, V value) 
	{
		if (key == null || value == null)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			EntryNode<K, V> currentNode = firstNode;
			
			while (currentNode != null && key.compareTo(currentNode.getKey()) != 0)
			{
				currentNode = currentNode.getNextNode();
			}
			
			if (currentNode != null && key.equals(currentNode.getKey()))
			{
				currentNode.setValue(value);
				return value;
			}
			else
			{
				EntryNode<K, V> newNode = new EntryNode<K, V>(firstNode, key, value);
				firstNode = newNode;
				numOfEntries++;
				return null;
			}
		}
	}

	/** Removes a specific entry from this dictionary. 
	 * @param key An object search key of the entry to be removed.
	 * @return Either the value that was associated with the search key or null if no such object exists.
	 */
	public V remove(K key) 
	{
		EntryNode<K, V> currentNode = firstNode;
		EntryNode<K, V> nodeBefore = null;
		V data = null;
		
		for (int i = 0; i < numOfEntries; i++)
		{
			if (key.equals(currentNode.getKey()))
			{
				break;
			}
			nodeBefore = currentNode;
			currentNode = currentNode.getNextNode();
		}
		
		if (currentNode == null) //Empty list or not found
		{
			return null;
		}
		else if (currentNode == firstNode && currentNode == tailNode) //Only entry
		{
			data = currentNode.getValue();
			firstNode = tailNode = null;
		}
		else if(currentNode.getNextNode() == null) //End entry
		{
			data = currentNode.getValue();
			nodeBefore.setNextNode(null);
			tailNode = nodeBefore;
		}
		else if (currentNode == firstNode) //Beginning entry
		{
			data = currentNode.getValue();
			firstNode = currentNode.getNextNode();
		}
		else //Middle entry
		{
			data = currentNode.getValue();
			nodeBefore.setNextNode(currentNode.getNextNode());
		}
		currentNode = null;
		numOfEntries--;

		return data;
	}
	
	/** Retrieves from this dictionary the value associated with a given search key.
	 * @param key An object search key of the entry to be retrieved.
	 * @return Either the value that is associated with the search key or null if no such object exists.
	 */
	public V getValue(K key) 
	{		
		EntryNode<K, V> currentNode = firstNode;
		
		for (int i = 0; i < numOfEntries; i++)
		{
			if (key.equals(currentNode.getKey()))
			{
				break;
			}
			currentNode = currentNode.getNextNode();
		}
		
		if (currentNode != null)
		{
			return currentNode.getValue();
		}
		else
		{
			return null;
		}
	}

	/** Sees whether a specific entry is in this dictionary. 
	 * @param key An object search key of the desired entry.
	 * @return True if key is associated with an entry in the dictionary.
	 */
	public boolean contains(K key) 
	{
		EntryNode<K, V> currentNode = firstNode;
		boolean result = false;
		
		for (int i = 0; i < numOfEntries; i++)
		{
			if (key.equals(currentNode.getKey()))
			{
				result = true;
				break;
			}
			currentNode = currentNode.getNextNode();
		}
		
		return result;
	}

	/** Creates an iterator that traverses all search keys in this dictionary. 
	 * @return An iterator that provides sequential access to the search keys in the dictionary.
	 */
	public Iterator<K> getKeyIterator() 
	{
		return new InnerKeyIterator();
	}
	
	//
	private class InnerKeyIterator implements Iterator<K>
	{
		private EntryNode<K, V> currentNode;
		//private boolean wasNextCalled;
		
		private InnerKeyIterator()
		{
			currentNode = firstNode;
			//wasNextCalled = false;
		}
		
		public boolean hasNext()
		{
			if (currentNode == null)
			{
				return false;
			}
			else
			{
				return currentNode.getKey() != null;
			}
		}
		
		public K next()
		{
			if (hasNext())
			{
				K data = currentNode.getKey();
				currentNode = currentNode.getNextNode();
				return data;
			}
			else
			{
				throw new NoSuchElementException("Illegal call to next(); iterator is after end of list.");
			}
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException("remove() is not supported by this iterator");
		}
	}

	/** Creates an iterator that traverses all values in this dictionary. 
	 * @return An iterator that provides sequential access to the search keys in the dictionary.
	 */
	public Iterator<V> getValueIterator() 
	{
		return new InnerValueIterator();
	}
	
	//
	private class InnerValueIterator implements Iterator<V>
	{
		private EntryNode<K, V> currentNode;
		//private boolean wasNextCalled;
		
		private InnerValueIterator()
		{
			currentNode = firstNode;
			//wasNextCalled = false;
		}
		
		public boolean hasNext()
		{
			if (currentNode == null)
			{
				return false;
			}
			else
			{
				return currentNode.getValue() != null;
			}
		}
		
		public V next()
		{
			if (hasNext())
			{
				V data = currentNode.getValue();
				currentNode = currentNode.getNextNode();
				return data;
			}
			else
			{
				throw new NoSuchElementException("Illegal call to next(); iterator is after end of list.");
			}
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException("remove() is not supported by this iterator");
		}
	}

	/** Sees whether this dictionary is empty.
	 * @return True if the dictionary is empty.
	 */
	public boolean isEmpty() 
	{
		return numOfEntries == 0;
	}

	/** Gets the size of this dictionary.
	 * @return The number of entries (key-value pairs) currently in the dictionary.
	 */
	public int getSize() 
	{
		return numOfEntries;
	}

	/** Removes all entries from this dictionary.
	 */
	public void clear() 
	{
		firstNode = tailNode = null;
	}

}
