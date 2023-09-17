/**
 * @author Renzo Svartz
 */

package Data_Structures;

/**
 * A class implementing a linked node
 * @param <T> general parameter
 */
public class Node<T> 
{
	private Node<T> nextNode;
	private T data;
	
	/**
	 * Constructor
	 * @param nextNode
	 * @param data
	 */
	public Node(Node<T> nextNode, T data)
	{
		this.nextNode = nextNode;
		this.data = data;
	}
	
	/**
	 * top node constructor
	 * @param data
	 */
	public Node(T data)
	{
		this(null, data);
	}
	
	/**
	 * Method used to set the next node linked to the current node
	 * @param nextNode the next node linked to the current node
	 */
	public void setNextNode(Node<T> nextNode)
	{
		this.nextNode = nextNode;
	}
	
	/**
	 * Method used to set the data stored in the current node
	 * @param data the data to store in the current node
	 */
	public void setData(T data)
	{
		this.data = data;
	}
	
	/**
	 * Method used to get the next node linked to the current node
	 * @return the next node linked to the current node
	 */
	public Node<T> getNextNode()
	{
		return nextNode;
	}
	
	/**
	 * Method used to get the data stored in the current node
	 * @return the data to store in the current node
	 */
	public T getData()
	{
		return data;
	}
}
