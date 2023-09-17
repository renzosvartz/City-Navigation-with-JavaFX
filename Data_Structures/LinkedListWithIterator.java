/**
 * @author Renzo Svartz
 */
package Data_Structures;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import Interfaces.ListWithIteratorInterface;

/**
 * Class implementing a linked list with an inner iterator class
 * @param <T> generic parameter
 */
public class LinkedListWithIterator<T> implements ListWithIteratorInterface<T>
{
	private Node<T> firstNode;
	private Node<T> lastNode;
	private int numOfEntries;
	
	/**
	 * Default constructor
	 */
	public LinkedListWithIterator()
	{
		firstNode = null;
		lastNode = null;
		numOfEntries = 0;
	}
	
	/**
	 * Copy constructor
	 * @param list list to copy
	 */
	public LinkedListWithIterator(LinkedListWithIterator<T> list)
	{
		firstNode = null;
		lastNode = null;
		numOfEntries = 0;
		
		for (int i = 0; i < list.getLength(); i++)
		{
			add(list.getEntry(i));
		}
	}
	
	/** Adds a new entry to the end of this list. 
	 * Entries currently in the list are unaffected.
	 * The list's size is increased by 1.
	 * @param newEntry The object to be added as a new entry.
	 */
	public void add(T newEntry)
	{
		System.out.println("Adding in list");
		
		Node<T> newNode = new Node<T>(newEntry);
		
		if(isEmpty())
		{
			firstNode = newNode;
		}
		else
		{
			lastNode.setNextNode(newNode);
		}
		lastNode = newNode; //lastNode always has to be set to newNode when adding to the back
		numOfEntries++;
		System.out.println("Total entries: " + numOfEntries);
	}
	
	/** Adds a new entry at a specified position within this list.
	 * Entries originally at and above the specified position
	 * are at the next higher position within the list.
	 * The list's size is increased by 1.
	 * @param newPosition An integer that specifies the desired position of the new entry. 
	 * @param newEntry The object to be added as a new entry. 
	 * @throws IndexOutOfBoundsException if either newPosition < 1 or newPosition > getLength() + 1.
	 */
	public void add(int givenPosition, T newEntry)
	{
		if (givenPosition >= 0 && givenPosition <= numOfEntries)
		{
			Node<T> newNode = new Node<T>(newEntry);
			
			if(isEmpty())
			{
				firstNode = newNode;
				lastNode = newNode;
			}
			else if (givenPosition == 0)
			{
				newNode.setNextNode(firstNode);
				firstNode = newNode;
			}
			else if (givenPosition == numOfEntries)
			{
				lastNode.setNextNode(newNode);
				lastNode = newNode;
			}
			else
			{
				Node<T> nodeBefore = getNodeAt(givenPosition - 1);
				Node<T> nodeAfter = getNodeAt(givenPosition + 1);
				nodeBefore.setNextNode(newNode);
				newNode.setNextNode(nodeAfter);
			}
			numOfEntries++;
		}
		else
		{
			throw new IndexOutOfBoundsException("Given position of add's new entry is out of bounds.");
		}
	}
	
	/** Removes the entry at a given position from this list.
	 * Entries originally at positions higher than the 
	 * given position are at the next lower position within the list,
	 * and the list's size is decreased by 1.
	 * @param givenPosition An integer that indicates the position of the entry to be removed.
	 * @return A reference to the removed entry.
	 * @throws IndexOutOfBoundsException if either givenPosition < 1 or givenPosition > getLength().
	 */
	public T remove(int givenPosition)
	{
		if (givenPosition >= 0 && givenPosition <= numOfEntries - 1)
		{
			T data;
			if (givenPosition == 0)
			{
				data = firstNode.getData();
				firstNode = firstNode.getNextNode();
			}
			else
			{
				Node<T> nodeToRemove = getNodeAt(givenPosition);
				Node<T> nodeBefore = getNodeAt(givenPosition - 1);
				Node<T> nodeAfter = getNodeAt(givenPosition + 1);
				data = nodeToRemove.getData();
				nodeBefore.setNextNode(nodeAfter);
				if (givenPosition == numOfEntries - 1)
				{
					lastNode = nodeBefore; //check boundary conditions
				}
			}
			numOfEntries--;
			return data;
		}
		else
		{
			throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
		}
	}
	
	/**
	 * Method locates and returns the node at a given index
	 * @param givenPosition index of the node
	 * @return the node at the given position
	 */
	private Node<T> getNodeAt(int givenPosition)
	{
		Node<T> currentNode = firstNode;

		for (int i = 0; i < givenPosition; i++)
		{
			currentNode = currentNode.getNextNode();
		}
			
		return currentNode;
	}
	
	/** Retrieves the entry at a given position in this list.
	 * @param givenPosition An integer that indicates the position of the desired entry.
	 * @return A reference to the indicated entry.
	 * @throws IndexOutOfBoundsException if either givenPosition < 1 or givenPosition > getLength().
	 */
	public T getEntry(int givenPosition)
	{
		if (givenPosition >= 0 && givenPosition <= numOfEntries - 1)
		{
			Node<T> currentNode = firstNode;
			for (int i = 0; i < givenPosition; i++)
			{
				currentNode = currentNode.getNextNode();
			}
			return currentNode.getData();
		}
		else
		{
			throw new IndexOutOfBoundsException("Given position of add's new entry is out of bounds.");
		}
	}
	
	/** Replaces the entry at a given position in this list. 
	 * @param givenPosition An integer that indicates the position of the entry to be replaced.
	 * @param newEntry The object that will replace the entry at the position givenPosition.
	 * @return The original entry that was replaced.
	 * @throws IndexOutOfBoundsException if either givenPosition < 1 or givenPosition > getLength().
	 */
	public T replace(int givenPosition, T newEntry)
	{
		if (givenPosition >= 0 && givenPosition <= numOfEntries - 1)
		{
			Node<T> currentNode = firstNode;
			for (int i = 0; i < givenPosition; i++)
			{
				currentNode = currentNode.getNextNode();
			}
			T data = currentNode.getData();
			currentNode.setData(newEntry);
			return data;
		}
		else
		{
			throw new IndexOutOfBoundsException("Given position of add's new entry is out of bounds.");
		}
	}
	
	/** Sees whether this list contains a given entry.
	 * @param anEntry The object that is the desired entry.
	 * @return True if the list contains anEntry, or false if not.
	 */
	public boolean contains(T anEntry)
	{
		boolean status = false;
		Node<T> currentNode = firstNode;

		for (int i = 0; i < numOfEntries; i++)
		{
			if (anEntry.equals(currentNode.getData()))
			{
				status = true;
				break;
			}
			currentNode = currentNode.getNextNode();
		}
		
		return status;
	}
	
	/** Retrieves all entries that are in this list in the order in which they occur in the list.
	 * @return A newly allocated array of all the entries in the list. If the list is empty, the returned array is empty.
	 */
	public T[] toArray()
	{	
		@SuppressWarnings("unchecked")
		T[] copy = (T[]) new Object[numOfEntries];
		Node<T> currentNode = firstNode;
		
		for (int i = 0; i < numOfEntries; i++)
		{
			copy[i] = currentNode.getData();
			currentNode = currentNode.getNextNode();
		}
		
		return copy;
	}
	
	/** Gets the length of this list.
	 * @return The integer number of entries currently in the list.
	 */
	public int getLength()
	{
		return numOfEntries;
	}

	/** Removes all entries from this list.
	 */
	public void clear()
	{
		firstNode = lastNode = null;
		numOfEntries = 0;
	}

	/** Sees whether this list is empty.
	 * @return True if the list is empty, or false if not.
	 */
	public boolean isEmpty() 
	{
		return numOfEntries == 0;
	}
	
	public Iterator<T> iterator()
	{
		return new IteratorForLinkedList();
	}
	
	//
	private class IteratorForLinkedList implements Iterator<T>
	{
		private Node<T> currentNode;
		private int nextPosition;
		private boolean wasNextCalled;
		
		private IteratorForLinkedList()
		{
			currentNode = firstNode;
			nextPosition = 0;
			wasNextCalled = false;
		}
		
		public boolean hasNext()
		{
			return currentNode != null;
		}
		
		public T next()
		{
			if (hasNext())
			{
				T data = currentNode.getData();
				currentNode = currentNode.getNextNode();
				nextPosition++;
				wasNextCalled = true;
				return data;
			}
			else
			{
				throw new NoSuchElementException("Illegal call to next(); iterator is after end of list.");
			}
		}
		
		public void remove()
		{
			if (wasNextCalled)
			{
				LinkedListWithIterator.this.remove(--nextPosition);
				wasNextCalled = false;
			}
			else
			{
				throw new IllegalStateException("Illegal call to remove(); next() was not called.");
			}
		}
	}
	
	public ListIterator<T> listIterator()
	{
		return new ListIteratorForLinkedList();
	}
	
	//
	private class ListIteratorForLinkedList implements ListIterator<T>
	{
		private enum Move {NEXT, PREVIOUS};
		
		private Node<T> currentNode;
		private int nextPosition;
		private boolean isRemoveOrSetLegal;
		private Move lastMove;
		
		private ListIteratorForLinkedList()
		{
			currentNode = firstNode;
			nextPosition = 0;
			isRemoveOrSetLegal = false;
			lastMove = null;
		}
		
		public boolean hasNext()
		{
			return currentNode != null;
		}
		
		public T next()
		{
			if (hasNext())
			{
				isRemoveOrSetLegal = true;
				lastMove = Move.NEXT;
				T data = currentNode.getData();
				currentNode = currentNode.getNextNode();
				nextPosition++;
				return data;
			}
			else
			{
				throw new NoSuchElementException("Illegal call to next(); iterator is after end of list.");
			}
		}
		
		public void remove()
		{
			if (isRemoveOrSetLegal)
			{
				if (lastMove.equals(Move.NEXT))
				{
					LinkedListWithIterator.this.remove(--nextPosition);
				}
				else if (lastMove.equals(Move.PREVIOUS))
				{
					LinkedListWithIterator.this.remove(nextPosition);
				}
				isRemoveOrSetLegal = false;
			}
			else
			{
				throw new IllegalStateException("Illegal call to remove(); next() or previous() was not called, or add() or remove() called since then.");
			}
		}
		
		public boolean hasPrevious()
		{
			return (nextPosition > 0 && nextPosition <= numOfEntries);
		}
		
		public T previous()
		{
			if (hasPrevious())
			{
				isRemoveOrSetLegal = true;
				lastMove = Move.PREVIOUS;
				return LinkedListWithIterator.this.getEntry(--nextPosition);
			}
			else
			{
				throw new NoSuchElementException("Illegal call to previous(); iterator is at beginning of list.");
			}
		}
		
		public int nextIndex()
		{
			if (hasNext())
			{
				return nextPosition;
			}
			else
			{
				return numOfEntries;
			}
		}
		
		public int previousIndex()
		{
			if (hasPrevious())
			{
				return nextPosition - 1;
			}
			else
			{
				return -1;
			}
		}
		
		public void add(T newEntry)
		{
			LinkedListWithIterator.this.add(nextPosition++, newEntry);
			isRemoveOrSetLegal = false;
		}
		
		public void set(T newEntry)
		{
			if (isRemoveOrSetLegal)
			{
				if (lastMove.equals(Move.NEXT))
				{
					LinkedListWithIterator.this.replace(nextPosition - 1, newEntry);
				}
				else if (lastMove.equals(Move.PREVIOUS))
				{
					LinkedListWithIterator.this.replace(nextPosition, newEntry);
				}
				isRemoveOrSetLegal = false;
			}
			else
			{
				throw new IllegalStateException("Illegal call to set(); next() or previous() was not called, or add() or remove() called since then.");
			}
		}
	}
}
