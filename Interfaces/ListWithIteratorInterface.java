/**
 * @author Renzo Svartz
 */
package Interfaces;

import java.util.Iterator;

/**
 * An interface representing a list that can create an iterator.
 * @param <T> a general parameter
 */
public interface ListWithIteratorInterface<T> extends ListInterface<T>, Iterable<T>
{
	public Iterator<T> iterator();
}
