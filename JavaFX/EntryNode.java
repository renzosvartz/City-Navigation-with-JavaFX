/**
 * @author Renzo Svartz
 */

package JavaFX;

/**
 * A class implementing a linked node
 * @param <T> general parameter
 */
public class EntryNode<K, V> 
{
	private EntryNode<K, V>  nextNode;
	private K key;
	private V value;
	
	/**
	 * Constructor
	 * @param nextNode
	 * @param data
	 */
	public EntryNode(EntryNode<K, V>  nextNode, K key, V value)
	{
		this.nextNode = nextNode;
		this.key = key;
		this.value = value;
	}
	
	/**
	 * top node constructor
	 * @param data
	 */
	public EntryNode(K key, V value)
	{
		this(null, key, value);
	}
	
	/**
	 * Method used to set the next node linked to the current node
	 * @param nextNode the next node linked to the current node
	 */
	public void setNextNode(EntryNode<K, V> nextNode)
	{
		this.nextNode = nextNode;
	}
	
	/**
	 * Method used to set the key stored in the current node
	 * @param key the key to store in the current node
	 */
	public void setKey(K key)
	{
		this.key = key;
	}
	
	/**
	 * Method used to set the value stored in the current node
	 * @param value the value to store in the current node
	 */
	public void setValue(V value)
	{
		this.value = value;
	}
	
	/**
	 * Method used to get the next node linked to the current node
	 * @return the next node linked to the current node
	 */
	public EntryNode<K, V> getNextNode()
	{
		return nextNode;
	}
	
	/**
	 * Method used to get the key stored in the current node
	 * @return the key to store in the current node
	 */
	public K getKey()
	{
		return key;
	}
	
	/**
	 * Method used to get the value stored in the current node
	 * @return the value to store in the current node
	 */
	public V getValue()
	{
		return value;
	}
}
