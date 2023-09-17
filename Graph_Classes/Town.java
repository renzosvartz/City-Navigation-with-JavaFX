/**
 * @author Renzo Svartz
 */
package Graph_Classes;

import java.util.Iterator;
import java.util.NoSuchElementException;

import Data_Structures.LinkedDictionary;
import Data_Structures.LinkedListWithIterator;
import Interfaces.DictionaryInterface;
import Interfaces.StackInterface;

/**
 * Class for representing a town (as a vector) in a graph
 */
public class Town implements Comparable<Town>
{
	private String name;
	private LinkedListWithIterator<Road> adjacencyList;
	private boolean visited;
	private Town predecessor; //on path
	private int cost; //on path
	private DictionaryInterface<String, StackInterface<String>> shortestPaths; //container for the shortest path to all other towns
	
	/**
	 * Parameterized Constructor
	 * @param name town name
	 */
	public Town(String name)
	{
		System.out.println("Creating Town " + name);
		this.name = name;
		adjacencyList = new LinkedListWithIterator<Road>();
		visited = false;
		predecessor = null;
		cost = 0;
		shortestPaths = new LinkedDictionary<String, StackInterface<String>>();
	}
	
	/**
	 * Copy constructor
	 * @param templateTown town to copy
	 */
	public Town(Town templateTown)
	{
		this.name = templateTown.getName();
		adjacencyList = new LinkedListWithIterator<Road>(templateTown.getList());
		visited = false;
		predecessor = null;
		cost = 0;
		shortestPaths = new LinkedDictionary<String, StackInterface<String>>(templateTown.getPaths());
	}
	
	/**
	 * Getter method for the town name
	 * @return the town name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Setter method for the town name
	 * @param name town name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Getter method for the shortest paths
	 * @return the shortest paths
	 */
	public DictionaryInterface<String, StackInterface<String>> getPaths()
	{
		return shortestPaths;
	}
	
	/**
	 * Setter method for the town adjacency list
	 * @param list adjacency list
	 */
	public void setPaths(DictionaryInterface<String, StackInterface<String>> shortestPaths)
	{
		this.shortestPaths = shortestPaths;
	}
	
	/**
	 * Getter method for the adjacency list
	 * @return the adjacency list
	 */
	public LinkedListWithIterator<Road> getList()
	{
		return adjacencyList;
	}
	
	/**
	 * Setter method for the town adjacency list
	 * @param list adjacency list
	 */
	public void setList(LinkedListWithIterator<Road> adjacencyList)
	{
		this.adjacencyList = adjacencyList;
	}
	
	/** Marks this vertex as visited. */
	public void visit()
	{
		visited = true;
	}
	
	/** Removes this vertex's visited mark. */
	public void unvisit()
	{
		visited = false;
	}
	
	/** Sees whether this vertex is marked as visited.
	@return True if the vertex is visited. */
	public boolean isVisited()
	{
		return visited;
	}
	
	/** Records the previous vertex on a path to this vertex.
	@param predecessor The vertex previous to this one along a path. */
	public void setPredecessor(Town predecessor)
	{
		this.predecessor = predecessor;
	}
	
	/** Gets the recorded predecessor of this vertex.
	@return Either this vertex's predecessor or null if no predecessor
	was recorded. */
	public Town getPredecessor()
	{
		return predecessor;
	}
	
	/** Sees whether a predecessor was recorded for this vertex.
	@return True if a predecessor was recorded. */
	public boolean hasPredecessor()
	{
		return predecessor != null;
	}
	
	/** Records the cost of a path to this vertex.
	@param newCost The cost of the path. */
	public void setCost(int cost)
	{
		this.cost = cost;
	}
	
	/** Gets the recorded cost of the path to this vertex.
	@return The cost of the path. */
	public int getCost()
	{
		return cost;
	}
	
	/**
	 * Method for adding the shortest path to a vertex (Town)
	 * @param destinationVertex destination vertex
	 * @param path shortest path to the destination
	 */
	public void addShortestPath(Town destinationVertex, StackInterface<String> path)
	{
		System.out.println("Adding shortest path between " + this.getName() + " and " + destinationVertex.getName() + " to shortest paths container.");
		shortestPaths.add(destinationVertex.getName(), path);
	}
	
	/**
	 * Method for adding the shortest path to a vertex (Town)
	 * @param destinationVertex destination vertex
	 * @param path shortest path to the destination
	 */
	public StackInterface<String> getShortestPath(Town destinationVertex)
	{
		return shortestPaths.getValue(destinationVertex.getName());
	}
	
	/**
	 * Method for determining if this town object is equal to another town object
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
			return name.equals(((Town) o2).getName());
		}
	}
	
	/**
	 * Method for comparing this town object and another town object
	 * @param o other town object
	 * @return the result of a string comparison 
	 */
	@Override
	public int compareTo(Town o) 
	{
		return name.compareTo(o.getName());
	}

	/**
	 * toString method returns the town's name
	 * @return String (town's name)
	 */
	public String toString()
	{
		return name;
	}
	
	/** Connects adds a road to a vertex
	The two vertices cannot be the same, and must not already
	have this edge between them. In a directed graph, the edge
	points toward the given vertex.
	@param Road road to add
	@return True if the edge is added, or false if not. */
	public boolean connect(Road road)
	{		
		if (road == null)
		{
			throw new NullPointerException();
		}
	
		System.out.println("Adding Road " + road.getName() + " between " + road.getSource() + " and " + road.getDestination());
		
		Town startVertex = road.getSource();
		Town endVertex = road.getDestination();
		
		//ensures no loops
		if (!startVertex.equals(endVertex))
		{
			//adjacency list
			Iterator<Town> neighbors = getNeighborIterator();
			
			//checks adjacency list for this edge to already exist
			boolean duplicateEdge = false;
			while (!duplicateEdge && neighbors.hasNext())
			{
				Town currentTown = neighbors.next();
				if (endVertex.equals(currentTown))
				{
					duplicateEdge = true;
				}
			}
			
			//if we don't find a duplicate, add the Edge(endVertex, weight) to the list for this vertex
			if (!duplicateEdge)
			{
				System.out.println("Adding " + road.getName() + " to " + this.getName());
				
				adjacencyList.add(road);
				
				System.out.println("Adding " + road.getName() + " to " + road.getDestination().getName());
				road.getDestination().getList().add(road); //

				System.out.println("Connected " + road.getSource() + " and " + road.getDestination());
				return true;
			}
		}
		
		System.out.println("Did not connect " + road.getSource() + " and " + road.getDestination());
		return false;
	}
	
	/*
	/** Connects this vertex and a given vertex with an unweighted edge.
	The two vertices cannot be the same, and must not already
	have this edge between them. In a directed graph, the edge
	points toward the given vertex.
	@param endVertex A vertex in the graph that ends the edge.
	@return True if the edge is added, or false if not.
	public boolean connect(Town endVertex, String description)
	{
		return connect(endVertex, 0, description);
	}
	*/
	
	/** Creates an iterator of this vertex's roads by iterating over the adjacency list
	@return An iterator of the edges of this vertex. */
	public Iterator<Road> getRoadIterator()
	{
		System.out.println("Creating RoadIterator");
		return adjacencyList.iterator();
	}
	
	/** Creates an iterator of this vertex's neighbors by following
	all edges that begin at this vertex.
	@return An iterator of the neighboring vertices of this vertex. */
	public Iterator<Town> getNeighborIterator()
	{
		return new NeighborIterator();
	}

	/** Creates an iterator of the weights of the edges to this
	vertex's neighbors.
	@return An iterator of edge weights for edges to neighbors of this
	vertex. */
	public Iterator<Integer> getWeightIterator()
	{
		return new WeightIterator();
	}
	
	/**
	 * Inner class implementing a neighbor iterator
	 * Used instead of calling adjacencyList.next().getEndVertex() while adjacencyList.next() would return an edge.
	 * Because the adjacencyList iterator next() call would return an edge, not the endVertex of the edge
	 * So we just make this class to obtain this outcome
	 * Then create an (neighbor) iterator object i.e. 
	 * Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
	 * Then calls neighbors.next() will return neighboring end vertices
	 */
	private class NeighborIterator implements Iterator<Town>
	{
		private Iterator<Road> edges; //iterator object with generic parameter <Edge> hasNext, next, remove()
		
		private NeighborIterator()
		{
			System.out.println("Creating NeighborIterator");
			edges = adjacencyList.iterator(); //returns a linked list (Node<Edge>) iterator that we (further) modify and access to create a neigbor iterator as explained above
		}
		
		public boolean hasNext()
		{
			return edges.hasNext();
		}
		
		public Town next()
		{
			if (edges.hasNext())
			{
				return edges.next().getDestination();
			}
			else
			{
				throw new NoSuchElementException();
			}
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * Inner class implementing a weight iterator
	 * Used instead of calling edgeList.next().geWeight()
	 * Because the edgeList iterator next() call would return an edge, not the weight of the edge
	 * So we just make this class to obtain this outcome
	 * Then create an (weight) iterator object i.e. 
	 * Iterator<Double> weights = getWeightIterator();
	 * Then calls neighbors.next() will return neighboring edge weights
	 */
	private class WeightIterator implements Iterator<Integer>
	{
		private Iterator<Road> edges; //iterator object with generic parameter <Edge> hasNext, next, remove()
		
		private WeightIterator()
		{
			System.out.println("Creating WeightIterator");
			edges = adjacencyList.iterator(); //returns a linked list (Node<Edge>) iterator that we (further) modify and access to create a weight iterator as explained above
		}
		
		public boolean hasNext()
		{
			return edges.hasNext();
		}
		
		public Integer next()
		{
			if (edges.hasNext())
			{
				return edges.next().getWeight();
			}
			else
			{
				throw new NoSuchElementException();
			}
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
}
