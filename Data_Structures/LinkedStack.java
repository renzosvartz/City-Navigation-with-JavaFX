/**
 * @author Renzo Svartz
 */

package Data_Structures;

import java.util.ArrayList;

import Exceptions.EmptyStackException;
import Interfaces.StackInterface;

/**
 * Generic class implementing a stack
 * @param <T> generic parameter
 */
public class LinkedStack<T> implements StackInterface<T>
{
	private Node<T> topNode;
	private int numOfElements;
	
	/**
	 * Default Constructor
	 */
	public LinkedStack()
	{
		topNode = null;
		numOfElements = 0;
	}

	/**
	 * Method used to push a new entry
	 * @param newEntry a new entry
	 */
	public void push(T newEntry)
	{
		topNode = new Node<T>(topNode, newEntry);
		numOfElements++;
	}
	
	/**
	 * Method used to pop the top entry
	 * @return the data stored in the top node
	 * @throws EmptyStackException an exception thrown when the stack is empty and attempted to pop().
	 */
	public T pop() throws EmptyStackException
	{
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		else
		{
			T topData = topNode.getData();
			topNode = topNode.getNextNode();
			numOfElements--;
			return topData;
		}
	}
	
	/**
	 * Method used to return the data stored in the top node without popping it
	 * @return the data stored in the top node
	 * @throws EmptyStackException an exception thrown when the stack is empty and attempted to pop().
	 */
	public T peek() throws EmptyStackException
	{
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		else
		{
			return topNode.getData();
		}
	}
	
	/**
	 * Method used to check if the stack is empty
	 * @return true if empty
	 */
	public boolean isEmpty()
	{
		if (topNode == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Method used to clear the stack
	 */
	public void clear()
	{
		topNode = null;
	}
	
	/**
	 * Turns Stack into ArrayList
	 * @return ArrayList of Stack
	 */
	public ArrayList<T> toArrayList()
	{
		Node<T> currentNode = topNode;
		
		ArrayList<T> arrayList = new ArrayList<T>();
		
		while (currentNode != null)
		{
			arrayList.add(currentNode.getData());
			currentNode = currentNode.getNextNode();
		}
		
		return arrayList;
	}
}
