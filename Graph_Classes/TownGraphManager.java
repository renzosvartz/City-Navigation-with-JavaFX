/**
 * @author Renzo Svartz
 */
package Graph_Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import Interfaces.TownGraphManagerInterface;

/**
 * Class implementing a Manager for a Graph containing Town's
 */
public class TownGraphManager implements TownGraphManagerInterface
{
	private Graph graph;
	
	/**
	 * Default Constructor
	 */
	public TownGraphManager()
	{
		graph = new Graph();
	}
	
	/**
	 * Adds a road with 2 towns and a road name
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @param roadName name of road
	 * @return true if the road was added successfully
	 * @throws Exception thrown if the edge is not added appropriately
	 */
	public boolean addRoad(String town1, String town2, int weight, String roadName) throws Exception
	{
		if ((addTown(town1) && addTown(town2)) || (containsTown(town1) && containsTown(town2)))
		{
			return graph.addEdge(getTown(town1), getTown(town2), weight, roadName) != null;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns the name of the road that both towns are connected through
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @return name of road if town 1 and town2 are in the same road, returns null if not
	 */
	public String getRoad(String town1, String town2)
	{
		Road road = null;
		Town sourceVertex = getTown(town1);
		Town destinationVertex = getTown(town2);
		
		if ((road = graph.getEdge(sourceVertex, destinationVertex)) != null)
		{
			return road.getName(); 
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Returns the name of the road that both towns are connected through
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @return name of road if town 1 and town2 are in the same road, returns null if not
	 */
	public Road getRoadObject(String town1, String town2)
	{
		Road road = null;
		Town sourceVertex = getTown(town1);
		Town destinationVertex = getTown(town2);
		
		if ((road = graph.getEdge(sourceVertex, destinationVertex)) != null)
		{
			return road; 
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Adds a town to the graph
	 * @param v the town's name  (lastname, firstname)
	 * @return true if the town was successfully added, false if not
	 */
	public boolean addTown(String v)
	{
		return graph.addVertex(new Town(v));
	}
	
	/**
	 * Gets a town with a given name
	 * @param name the town's name 
	 * @return the Town specified by the name, or null if town does not exist
	 */
	public Town getTown(String name)
	{
		return graph.getTown(new Town(name));
	}
	
	/**
	 * Determines if a town is already in the graph
	 * @param v the town's name 
	 * @return true if the town is in the graph, false if not
	 */
	public boolean containsTown(String v)
	{
		return graph.containsVertex(new Town(v));
	}
	
	/**
	 * Determines if a road is in the graph
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @return true if the road is in the graph, false if not
	 */
	public boolean containsRoadConnection(String town1, String town2)
	{
		return graph.containsEdge(getTown(town1), getTown(town2));
	}
	
	/**
	 * Deletes a road from the graph
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @param roadName the road name
	 * @return true if the road was successfully deleted, false if not
	 */
	public boolean deleteRoadConnection(String town1, String town2, String road)
	{
		Road targetRoad = getRoadObject(town1, town2);
		if (graph.removeEdge(getTown(town1), getTown(town2), targetRoad.getWeight(), road) != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Deletes a town from the graph
	 * @param v name of town (lastname, firstname)
	 * @return true if the town was successfully deleted, false if not
	 */
	public boolean deleteTown(String v)
	{
		return graph.removeVertex(getTown(v));
	}

	/**
	 * Creates an arraylist of all road titles in sorted order by road name
	 * @return an arraylist of all road titles in sorted order by road name
	 */
	public ArrayList<String> allRoads()
	{
		//gets all roads
		Set<Road> roads = graph.edgeSet();
		
		System.out.println(roads.size());
		
		//road iterator
		Iterator<Road> roadIterator = roads.iterator();
		
		//ArrayList for all roads
		ArrayList<String> titles = new ArrayList<String>();
		
		while (roadIterator.hasNext())
		{
			titles.add(roadIterator.next().getName());
		}
		
		System.out.println("Sorting all Roads ArrayList");
		titles.sort((o1, o2) -> o1.compareTo(o2));
		
		return titles;
	}
	
	/**
	 * Creates an arraylist of all towns in alphabetical order (last name, first name)
	 * @return an arraylist of all towns in alphabetical order (last name, first name)
	 */
	public ArrayList<String> allTowns()
	{
		//gets all roads
		Set<Town> towns = graph.vertexSet();
		
		System.out.println(towns.size());
		
		//road iterator
		Iterator<Town> townIterator = towns.iterator();
		
		//ArrayList for all roads
		ArrayList<String> names = new ArrayList<String>();
		
		while (townIterator.hasNext())
		{
			names.add(townIterator.next().getName());
		}
		
		System.out.println("Sorting all Towns ArrayList");
		names.sort((o1, o2) -> o1.compareTo(o2));
		
		return names;
	}
	
	/**
	 * Returns the shortest path from town 1 to town 2
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @return an Arraylist of roads connecting the two towns together, null if the
	 * towns have no path to connect them.
	 */
	public ArrayList<String> getPath(String town1, String town2)
	{
		return graph.shortestPath(getTown(town1), getTown(town2));
	}
	
	/**
	 * Populates graph with data from file
	 * @param file data file
	 * @throws Exception thrown if Road is not added properly
	 */
	public void populateTownGraph(File file) throws FileNotFoundException, IOException, Exception
	{
		Scanner inputFile = new Scanner(file);
		
		while (inputFile.hasNextLine())
		{
			System.out.println("Found input from file " + file.getName());
			
			inputFile.useDelimiter(",");
			String roadName = inputFile.next();
			System.out.println("Road Name: " + roadName);
			inputFile.useDelimiter("");
			inputFile.next(",");
			inputFile.useDelimiter(";");
			int distance = inputFile.nextInt();
			System.out.println("Distance: " + distance);
			inputFile.useDelimiter("");
			inputFile.next(";");
			inputFile.useDelimiter(";");
			Town sourceTown = new Town(inputFile.next());
			System.out.println("Source Town: " + sourceTown.getName());
			inputFile.reset();
			inputFile.useDelimiter("");
			inputFile.next(";");
			inputFile.reset();
			Town destinationTown = new Town(inputFile.nextLine());
			System.out.println("Destination Town: " + destinationTown.getName());
			
			//adds vertices
			if (!graph.containsVertex(sourceTown))
			{
				graph.addVertex(sourceTown);
			}
			else
			{
				sourceTown = graph.getTown(sourceTown);
			}
			if (!graph.containsVertex(destinationTown))
			{
				graph.addVertex(destinationTown);
			}
			else
			{
				destinationTown = graph.getTown(destinationTown);
			}
			
			//adds edge
			graph.addEdge(sourceTown, destinationTown, distance, roadName);
		}
		
		System.out.println("Closing file " + file.getName());
		inputFile.close();
	}
}
