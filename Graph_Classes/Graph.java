/**
 * @author Renzo Svartz
 */
package Graph_Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

import Data_Structures.LinkedDictionary;
import Data_Structures.LinkedListWithIterator;
import Data_Structures.LinkedStack;
import Interfaces.DictionaryInterface;
import Interfaces.GraphInterface;

/**
 * Class implementing a graph
 */
public class Graph implements GraphInterface<Town, Road>
{
	//Dictionary (or list) for the vertices, container
	private DictionaryInterface<String, Town> towns; //<name, town>
	private int edgeCount;

	/**
	 * Default constructor creates a container for the vertices (and their adjacency lists [edges])
	 */
	public Graph()
	{
		System.out.println("Creating Graph");
		towns = new LinkedDictionary<String, Town>();
		edgeCount = 0;
	}
	
    /**
     * Returns an edge connecting source vertex to target vertex if such
     * vertices and such edge exist in this graph. Otherwise returns
     * null. If any of the specified vertices is null
     * returns null
     * In undirected graphs, the returned edge may have its source and target
     * vertices in the opposite order.
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @return an edge connecting source vertex to target vertex.
     */
	public Road getEdge(Town sourceVertex, Town destinationVertex) 
	{
		//null vertices
		if (sourceVertex == null || destinationVertex == null)
		{
			throw new NullPointerException();
		}
		//valid vertices
		else if (towns.contains(sourceVertex.getName()) && towns.contains(destinationVertex.getName()))
		{
			System.out.println("Getting edge between " + sourceVertex + " and " + destinationVertex);
			
			Road road = null;
			
			//ensures no loops
			if (!sourceVertex.equals(destinationVertex))
			{
				//adjacency list
				Iterator<Road> roads = sourceVertex.getRoadIterator();
				
				//checks adjacency list for this edge to exist
				while (roads.hasNext())
				{
					road = roads.next();
					
					//found road
					if ((sourceVertex.equals(road.getSource()) && destinationVertex.equals(road.getDestination())) || (sourceVertex.equals(road.getDestination()) && destinationVertex.equals(road.getSource())))
					{
						System.out.println("Found Road " + road.getName() + " between " + sourceVertex + " and " + destinationVertex);
						return road;
					}
				}
			}
		}
		else
		{
			throw new IllegalArgumentException();
		}
		
		return null;
	}

    /**
     * Creates a new edge in this graph, going from the source vertex to the
     * target vertex, and returns the created edge. 
     * The source and target vertices must already be contained in this
     * graph. If they are not found in graph IllegalArgumentException is
     * thrown.
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight weight of the edge
     * @param description description for edge
     * @return The newly created edge if added to the graph, otherwise null.
     * @throws Exception thrown if the edge is not added appropriately
     * @throws IllegalArgumentException if source or target vertices are not
     * found in the graph.
     * @throws NullPointerException if any of the specified vertices is null.
     */
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) throws Exception
	{
		Road road = null;
		
		//null vertices
		if (sourceVertex == null || destinationVertex == null)
		{
			throw new NullPointerException();
		}
		//valid vertices
		else if (towns.contains(sourceVertex.getName()) && towns.contains(destinationVertex.getName()))
		{
			System.out.println("Attempting to add Road " + description + " between " + sourceVertex + " and " + destinationVertex);
			
			road = new Road(sourceVertex, destinationVertex, weight, description);
			
			//successful edge addition, a vertex operation
			if (sourceVertex.connect(road))
			{
				System.out.println("Road connected.");
				edgeCount++;
			}
			else
			{
				throw new Exception("Road not connected.");
			}
		}
		else
		{
			throw new IllegalArgumentException();
		}
		return road;
	}

    /**
     * Adds the specified vertex to this graph if not already present. More
     * formally, adds the specified vertex, v, to this graph if
     * this graph contains no vertex u such that
     * u.equals(v). If this graph already contains such vertex, the call
     * leaves this graph unchanged and returns false. In combination
     * with the restriction on constructors, this ensures that graphs never
     * contain duplicate vertices.
     * @param v vertex to be added to this graph.
     * @return true if this graph did not already contain the specified
     * vertex.
     * @throws NullPointerException if the specified vertex is null.
     */
	public boolean addVertex(Town v) 
	{
		if (v == null)
		{
			throw new NullPointerException();
		}
		else if (!towns.contains(v.getName()))
		{
			System.out.println("Adding Town " + v.getName());
			return towns.add(v.getName(), v) == null;
		}
		else
		{
			System.out.println("Did not (re)add Town " + v.getName());
			return false;
		}
	}

    /**
     * Returns true if and only if this graph contains an edge going
     * from the source vertex to the target vertex. In undirected graphs the
     * same result is obtained when source and target are inverted. If any of
     * the specified vertices does not exist in the graph, or if is
     * null, returns false.
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @return true if this graph contains the specified edge.
     */
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) 
	{
		return getEdge(sourceVertex, destinationVertex) != null;
	}

    /**
     * Returns true if this graph contains the specified vertex. More
     * formally, returns true if and only if this graph contains a
     * vertex u such that u.equals(v). If the
     * specified vertex is null returns false.
     * @param v vertex whose presence in this graph is to be tested.
     * @return true if this graph contains the specified vertex.
     */
	public boolean containsVertex(Town v) 
	{
		if (v == null)
		{
			return false;
		}
		else
		{
			return towns.contains(v.getName());
		}
	}

    /**
     * Returns a set of the edges contained in this graph. The set is backed by
     * the graph, so changes to the graph are reflected in the set. If the graph
     * is modified while an iteration over the set is in progress, the results
     * of the iteration are undefined.
     * @return a set of the edges contained in this graph.
     */
	public Set<Road> edgeSet() 
	{
		System.out.println("Creating edge Set");
		
		//Set for roads
		Set<Road> setOfRoads = new HashSet<Road>();
		
		//Town Iterator
		Iterator<Town> townIterator = towns.getValueIterator();
		
		//currentTown
		Town currentTown = null;
		
		//Loop through towns, adding the roads
		while (townIterator.hasNext())
		{
			currentTown = townIterator.next();
			
			System.out.println(currentTown.getName());
			
			//Road Iterator
			Iterator<Road> roadIterator = currentTown.getRoadIterator();
			
			Road currentRoad = null;
			
			//adds the roads to the set
			while (roadIterator.hasNext())
			{
				currentRoad = roadIterator.next();
				
				System.out.println("Found Road " + currentRoad);
				
				if (!setOfRoads.contains(currentRoad))
				{
					setOfRoads.add(currentRoad); //if the set does not contain A to B, add it
					System.out.println("Added Road " + currentRoad.getName());
				}
				else
				{
					System.out.println("Did not re(add) Road " + currentRoad.getName());
				}
			}
		}
		
		return setOfRoads;
	}

    /**
     * Returns a set of the vertices contained in this graph. The set is backed
     * by the graph, so changes to the graph are reflected in the set. If the
     * graph is modified while an iteration over the set is in progress, the
     * results of the iteration are undefined.
     * @return a set view of the vertices contained in this graph.
     */
	public Set<Town> vertexSet() 
	{
		System.out.println("Creating vertex Set");
		
		//Set for roads
		Set<Town> setOfTowns = new HashSet<Town>();
		
		//Town iterator
		Iterator<Town> townIterator = towns.getValueIterator();
		
		//adds Towns
		while (townIterator.hasNext())
		{
			Town currentTown = townIterator.next();
			setOfTowns.add(currentTown);
			System.out.println("Added Town " + currentTown.getName());
		}
		
		return setOfTowns;
	}
	
    /**
     * Returns a set of all edges touching the specified vertex (also
     * referred to as adjacent vertices). If no edges are
     * touching the specified vertex returns an empty set.
     * @param vertex the vertex for which a set of touching edges is to be
     * returned.
     * @return a set of all edges touching the specified vertex.
     * @throws IllegalArgumentException if vertex is not found in the graph.
     * @throws NullPointerException if vertex is null.
     */
	public Set<Road> edgesOf(Town vertex) 
	{
		if (vertex == null)
		{
			throw new NullPointerException();
		}
		else if (!towns.contains(vertex.getName()))
		{
			throw new IllegalArgumentException();
		}
		else
		{
			System.out.println("Finding Roads of Town " + vertex.getName());
			
			//Set for roads
			Set<Road> setOfRoads = new HashSet<Road>();
			
			//Town Iterator
			Iterator<Road> roadIterator = vertex.getRoadIterator();
			
			//Loop through towns, adding the roads
			while (roadIterator.hasNext())
			{
				Road currentRoad = roadIterator.next();
				setOfRoads.add(currentRoad);
				System.out.println("Added Road " + currentRoad.getName());
			}
			
			return setOfRoads;
		}
	}

    /**
     * Removes an edge going from source vertex to target vertex, if such
     * vertices and such edge exist in this graph. 
     * If weight >- 1 it must be checked
     * If description != null, it must be checked 
     * Returns the edge if removed
     * or null otherwise.
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight weight of the edge
     * @param description description of the edge
     * @return The removed edge, or null if no edge removed.
     */
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) 
	{
		if (weight < 0)
		{
			throw new IllegalArgumentException();
		}
		else if (description == null)
		{
			throw new NullPointerException();
		}
		
		//target Road
		Road targetRoad = getEdge(sourceVertex, destinationVertex);
	
		//the road exists
		if (targetRoad != null)
		{
			System.out.println("Removing Road " + targetRoad.getName());
			
			//current Road (of adjacency list of the source vertex)
			Road currentRoad = null;
			
			//adjacency list of the source vertex and destination vertex
			LinkedListWithIterator<Road> sourceList = sourceVertex.getList();
			LinkedListWithIterator<Road> destinationList = destinationVertex.getList();
			
			//finds the target road amongst the roads of the source vector
			for (int i = 0; i < sourceList.getLength(); i++)
			{
				currentRoad = sourceList.getEntry(i);
				
				if (currentRoad.equals(targetRoad))
				{
					sourceList.remove(i);
					System.out.println("Removed Road " + targetRoad.getName() + " from source Town " + sourceVertex.getName());
					break;
				}
			}
			
			//finds the target road amongst the roads of the destination vector
			for (int i = 0; i < destinationList.getLength(); i++)
			{
				currentRoad = destinationList.getEntry(i);
				
				if (currentRoad.equals(targetRoad))
				{
					destinationList.remove(i);
					System.out.println("Removed Road " + targetRoad.getName() + " from destination Town " + destinationVertex.getName());
					return currentRoad;
				}
			}
		}
		
		System.out.println("Unsuccessfully removed Road " + targetRoad.getName());
		return null;
	}

	  /**
     * Retrieves the specified vertex from this graph
     * Returns true if the graph contained the specified vertex.
     * If the specified vertex is null returns null.
     * @param v vertex to be retrieved from this graph, if present.
     * @return true if the graph contained the specified vertex; null otherwise.
     */
	public Town getTown(Town v) 
	{
		if (v == null || !containsVertex(v))
		{
			return null;
		}
		
		//vertex exists in graph
		return towns.getValue(v.getName());
	}
	
	/**
	 * Getter method for edge count
	 * @return edge count
	 */
	public int getEdgeCount()
	{
		return edgeCount;
	}
	
    /**
     * Removes the specified vertex from this graph including all its touching
     * edges if present. More formally, if the graph contains a vertex 
     * u such that u.equals(v), the call removes all edges
     * that touch u and then removes u itself. If no
     * such u is found, the call leaves the graph unchanged.
     * Returns true if the graph contained the specified vertex. (The
     * graph will not contain the specified vertex once the call returns).
     * If the specified vertex is null returns false.
     * @param v vertex to be removed from this graph, if present.
     * @return true if the graph contained the specified vertex;
     * false otherwise.
     */
	public boolean removeVertex(Town v) 
	{
		if (v == null || !containsVertex(v))
		{
			return false;
		}
		
		//vertex exists in graph
			
		//Town iterator
		Iterator<Town> townIterator = towns.getValueIterator();
			
		//current Town and Road to remove
		Town currentTown = null;
		Road roadToRemove = null;
		
		System.out.println("Removing Town " + v.getName());

		System.out.println("Removing all Roads connected to " + v.getName());
		//delete roads connecting other vertices to the argument vertex
		for (int i = 0; i < towns.getSize(); i++)
		{
			//gets next town
			currentTown = townIterator.next();
				
			if (!currentTown.equals(v))
			{
				if ((roadToRemove = getEdge(currentTown, v)) != null)
				{
					removeEdge(currentTown, v, roadToRemove.getWeight(), roadToRemove.getName());
				}
			}
		}
			
		//delete vertex
		towns.remove(v.getName());
		System.out.println("Removed Town " + v.getName());
		
		return true;
	}

    /**
     * Find the shortest path from the sourceVertex to the destinationVertex
     * call the dijkstraShortestPath with the sourceVertex
     * @param sourceVertex starting vertex
     * @param destinationVertex ending vertex
     * @return An arraylist of Strings that describe the path from sourceVertex
     * to destinationVertex
     * They will be in the format: startVertex "via" Edge "to" endVertex weight
	 * As an example: if finding path from Vertex_1 to Vertex_10, the ArrayList<String>
	 * would be in the following format(this is a hypothetical solution):
	 * Vertex_1 via Edge_2 to Vertex_3 4 (first string in ArrayList)
	 * Vertex_3 via Edge_5 to Vertex_8 2 (second string in ArrayList)
	 * Vertex_8 via Edge_9 to Vertex_10 2 (third string in ArrayList)
     */   
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) 
	{
		dijkstraShortestPath(sourceVertex);
		
		ArrayList<String> result = new ArrayList<String>();
		
		//enhance with disjoint 
		System.out.println("Creating ArrayList of the shortest path from  " + sourceVertex.getName() + " to " + destinationVertex.getName());
		
		LinkedStack<String> path = (LinkedStack<String>) sourceVertex.getShortestPath(destinationVertex);
		
		if (path == null)
		{
			return result;
		}
		else
		{
			result.addAll(path.toArrayList());
			return result;
		}
	}

    /**
     * Dijkstra's Shortest Path Method.  Internal structures are built which
     * hold the ability to retrieve the path, shortest distance from the
     * sourceVertex to all the other vertices in the graph, etc.
     * @param sourceVertex the vertex to find shortest path from
     */
	public void dijkstraShortestPath(Town sourceVertex) 
	{		
		//town iterator
		Iterator<Town> townIterator = towns.getValueIterator();
		
		//destination Town
		Town destinationVertex = null;
		
		//loops through towns finding the shortest path to each destination from the source
		while (townIterator.hasNext())
		{
			resetVertices();
			
			destinationVertex = townIterator.next();
			
			//different endpoints - valid evaluation
			if (!destinationVertex.equals(sourceVertex))
			{
				System.out.println("Finding path between "  + sourceVertex.getName() + " and " + destinationVertex.getName());
				
				//Queue for traversing (unvisited) nodes/vertices
				PriorityQueue<EntryPQ> townPriorityQueue = new PriorityQueue<EntryPQ>((o1, o2) -> o1.getCostFromOrigin() - o2.getCostFromOrigin());
				
				//adds origin within an entry to priorityqueue, then continues from here adding it's unvisited neighbors to the priorityqueue
				townPriorityQueue.add(new EntryPQ(sourceVertex, 0, null));
				
				//flag to end dijkstra's path algorithm
				boolean done = false;
						
				//(while there are) visits all vertices once and enqueues their neighbors
				while (!done && !townPriorityQueue.isEmpty())
				{					
					//accesses priorityqueue vertex through entry
					EntryPQ entry = townPriorityQueue.remove();
					Town town = entry.getVertex();

					//process unvisited vertex
					if (!town.isVisited())
					{
						//visit, cost (from origin), predecessor
						town.visit();
						town.setCost(entry.getCostFromOrigin());
						town.setPredecessor(entry.getPredecessor());
								
						//priorityqueue vertex is the end vertex therefore we have found the cheapest path at this processing point
						if (town.equals(destinationVertex))
						{
							done = true;
						}
						else //else priority enqueue all the unvisited neighbors, visited neighbors will be currently being reached via longer path
						{
							//adjacency list and corresponding edge weight
							Iterator<Town> neighbors = town.getNeighborIterator();
							Iterator<Integer> weights = town.getWeightIterator();
									
							//sequential search for unvisited neighbors, adding them (and their path totals) to the priority queue.
							while (neighbors.hasNext())
							{
								Town neighbor = neighbors.next();
								Integer weight = weights.next();
										
								if (!neighbor.isVisited())
								{
									townPriorityQueue.add(new EntryPQ(neighbor, weight + town.getCost(), town));
								}
							}
						}
					}	
				}
				
				//found path
				if (done)
				{
					System.out.println("Found shortest path between " + sourceVertex.getName() + " and " + destinationVertex.getName());
					
					//Stack for storing path
					LinkedStack<String> path = new LinkedStack<String>();
					
					//backtracks storing path
					Town originalDestination = destinationVertex;
					System.out.println(destinationVertex.getPredecessor().getName() + " via " + getEdge(destinationVertex.getPredecessor(), destinationVertex).getName() + " to " + destinationVertex.getName() + " " + getEdge(destinationVertex.getPredecessor(), destinationVertex).getWeight() + " mi");
					path.push(destinationVertex.getPredecessor().getName() + " via " + getEdge(destinationVertex.getPredecessor(), destinationVertex).getName() + " to " + destinationVertex.getName() + " " + getEdge(destinationVertex.getPredecessor(), destinationVertex).getWeight() + " mi");
					while (destinationVertex.hasPredecessor())
					{
						destinationVertex = destinationVertex.getPredecessor();
						if (destinationVertex.hasPredecessor())
						{
							System.out.println(destinationVertex.getPredecessor().getName() + " via " + getEdge(destinationVertex.getPredecessor(), destinationVertex).getName() + " to " + destinationVertex.getName() + " " + getEdge(destinationVertex.getPredecessor(), destinationVertex).getWeight() + " mi");
							path.push(destinationVertex.getPredecessor().getName() + " via " + getEdge(destinationVertex.getPredecessor(), destinationVertex).getName() + " to " + destinationVertex.getName() + " " + getEdge(destinationVertex.getPredecessor(), destinationVertex).getWeight() + " mi");
						}
					}
										
					//Adds path for this destination to this source's dictionary of shortest paths
					sourceVertex.addShortestPath(originalDestination, path);
					
					System.out.println(path.toArrayList().toString());
				}
				else //disjoint towns
				{
					System.out.println("Towns " + sourceVertex.getName() + " and " + destinationVertex.getName() + " are disjoint.");
				}
			}
		}
		
		resetVertices();
	}

	/**
	 * Inner class for vertices that are placed in a priority queue
	 */
	private class EntryPQ
	{
		private Town vertex;
		private int costFromOrigin;
		private Town predecessor;
		
		private EntryPQ(Town vertex, int costFromOrigin, Town predecessor)
		{
			System.out.println("Creating EntryPQ for Town " + vertex.getName() + " with cost " + costFromOrigin + " from " + (predecessor == null? "Nowhere" : predecessor.getName()));
			this.vertex = vertex;
			this.costFromOrigin = costFromOrigin;
			this.predecessor = predecessor;
		}
		
		private Town getVertex()
		{
			return vertex;
		}
		
		private Town getPredecessor()
		{
			return predecessor;
		}
		
		private int getCostFromOrigin()
		{
			return costFromOrigin;
		}
		
		@SuppressWarnings("unused")
		private void setCostFromOrigin(int costFromOrigin)
		{
			this.costFromOrigin = costFromOrigin;
		}
	}
	
	/**
	 * Method used to reset vertices for algorithms to work
	 */
	private void resetVertices()
	{
		System.out.println("Resetting vertices");
		
		//to access all the vertices we instantiate an iterator object assigned to the dictionary's value iterator
		Iterator<Town> townIterator = towns.getValueIterator();
		
		while (townIterator.hasNext())
		{
			Town currentTown = townIterator.next();
			currentTown.unvisit();
			currentTown.setPredecessor(null);
			currentTown.setCost(0);
		}
	}
	
}
