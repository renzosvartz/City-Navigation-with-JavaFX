/**
 * @author Renzo Svartz
 */

package Exceptions;

/**
* The EmptyStackException class is an exception class thrown if the stack is empty
*/
@SuppressWarnings("serial")
public class EmptyStackException extends Exception 
{
	/**
	 * Default constructor
	 */
	public EmptyStackException()
	{
		this("The stack is empty. Cannot pop stack.");
	}
	
	/**
	 * Constructor used to pass an error string to the superclass constructor
	 * @param error The informative error string
	 */
	public EmptyStackException(String error)
	{
		super(error);
	}
}