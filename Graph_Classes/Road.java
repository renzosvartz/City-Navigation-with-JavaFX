/**
 * @author Renzo Svartz
 */
package Graph_Classes;

public class Road implements Comparable<Road>
{
	private Town source;
	private Town destination;
	private int distance;
	private String name;
	
	/**
	 * Parameterized Constructor for weighted (edges) roads
	 * @param source source town
	 * @param destination destination town
	 * @param distance distance between town
	 * @param name name of road
	 */
	public Road(Town source, Town destination, int distance, String name)
	{
		System.out.println("Creating Road from " + source + " to " + destination + " named " + name);
		this.source = source;
		this.destination = destination;
		this.distance = distance;
		this.name = name;
	}
	
	/**
	 * Parameterized Constructor for unweighted (edges) roads
	 * @param source source town
	 * @param destination destination town
	 * @param name name of road
	 */
	public Road(Town source, Town destination, String name)
	{
		this.source = source;
		this.destination = destination;
		this.distance = 1;
		this.name = name;
	}

	/**
	 * Getter method for the (beginning vertex) source
	 * @return (beginning vertex) source
	 */
	public Town getSource()
	{
		return source;
	}

	/**
	 * Setter method for the (beginning vertex) source
	 * @param destination (beginning vertex) source
	 */
	public void setSource(Town source)
	{
		this.source = source;
	}
	
	/**
	 * Getter method for the (end vertex) destination
	 * @return (end vertex) destination
	 */
	public Town getDestination()
	{
		return destination;
	}

	/**
	 * Setter method for the (end vertex) destination
	 * @param destination (end vertex) destination
	 */
	public void setDestination(Town destination)
	{
		 this.destination = destination;
	}
	 
	/**
	 * Getter method for the weight
	 * @return edge weight
	 */
	public int getWeight()
	{
		return distance;
	}
	 
	/**
	 * Setter method for the weight
	 * @return distance road distance
	 */
	public void setWeight(int distance)
	{
		this.distance = distance;
	}
	
	/**
	 * Getter method for the name
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	 
	/**
	 * Setter method for the weight
	 * @return distance road distance
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Method determines if this road (edge) contains a specific town (vertex)
	 * @param town town
	 * @return true if contains
	 */
	public boolean contains(Town town)
	{
		return source.equals(town) || destination.equals(town);
	}
	
	/**
	 * Hash code producing method
	 * @return the hash code for the town name
	 */
	public int hashCode()
	{
		return name.hashCode();
	}
	
	/**
	 * Method for determining if this road object is equal to another road object (same source & destination)
	 * @param o2 other (town) object
	 * @return true if equal
	 */
	public boolean equals(Object o2)
	{
		if (o2 == null || getClass() != o2.getClass())
		{
			return false;
		}
		else
		{
			return source.equals(((Road) o2).getSource()) && destination.equals(((Road) o2).getDestination()) || source.equals(((Road) o2).getDestination()) && destination.equals(((Road) o2).getSource());
		}
	}
	 
	/**
	* Method for comparing this road object and another road object
	* @param o other town object
	* @return the result of the comparison 
	*/
	@Override
	public int compareTo(Road o) 
	{
		return distance - o.distance;
	}
	
	/**
	 * toString method returns the road's information
	 * @return String containing the road's information
	 */
	public String toString()
	{
		return "Source: " + source + ". " + "Destination: " + destination + ". " + "Distance: " + distance + ". " + "Road Name: " + name + ". ";
	}
}
