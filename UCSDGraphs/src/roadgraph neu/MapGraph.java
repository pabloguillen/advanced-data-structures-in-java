/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;




import geography.GeographicPoint;
import roadgraph.Intersection;
import roadgraph.Road;
import roadgraph.Searcher;
import roadgraph.BFSSearch;
import roadgraph.DijkstraSearcher;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and user
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {

	//HashMap which maps locations (GeographicPoints) with nodes (Intersection)
	private HashMap<GeographicPoint, Intersection> nodes;
	
	//HashMap which maps street names (String) with streets (Road)
	private HashSet<Road> streets;
	
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{		
		nodes = new HashMap<GeographicPoint, Intersection> ();
		streets = new HashSet<Road> ();
	}
	
	
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return nodes.size();
	}
	
	//added by learner (02.09.21)
	
	/** 
	 * Get all GeographicPoints in the graph
	 * @return The set of GeographicPoints in the graph.
	 */
	public Set<GeographicPoint> getGeographicPoints () {
		return nodes.keySet();
	}
	
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<Intersection> getVertices(){
		Set<Intersection> intersections = new HashSet<Intersection> ();
		for (GeographicPoint gp : nodes.keySet()) {
		intersections.add(nodes.get(gp));
		}
	return intersections;
	}
	
	
	/** 
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		return streets.size();
	}
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location) {
		if (location == null || this.nodes.containsKey(location) ) {
			return false;
		}
		else {
			Intersection intersec = new Intersection(location);
			this.nodes.put(location, intersec);
			//System.out.println("Node created... " + location);
			return true;
		}
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		
		Road r = new Road(from, to, roadName, roadType, length);
		
		if (nodes.get(from) != null && nodes.get(to) != null) {
			nodes.get(from).addNeighbor(nodes.get(to),r);
			streets.add(r);
			//System.out.println("Edge between node " + from + " to " + to + " created.");
		}
		
		
	
		else if (nodes.get(from) == null && nodes.get(to) != null){
			throw new IllegalArgumentException ("Error: Departure node does not exist.");
			
		}
		else if (nodes.get(from) != null && nodes.get(to) == null){
			throw new IllegalArgumentException ("Error: Destination node does not exist.");
		}
		else if (length <= 0) {
			throw new IllegalArgumentException ("Error: Road length is smaller than zero.");
		}
		
	}
	
	public HashMap<GeographicPoint, Intersection> getNodes (){
		return this.nodes;
	}

	/** 
	 * Find the path from start to goal using breadth first search
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		
		//Visualization
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	
	/** 
	 * Find the path from start to goal using breadth first search
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		Searcher bfs = new BFSSearch(start, goal, this.nodes);
		//Check Preconditions
		if (!bfs.checkPreconditions()) {
			return null;
		} else {
			//search
			HashMap<GeographicPoint, GeographicPoint> visitednodes = bfs.startBFSSearch(nodeSearched);
			
			//iterate of parent map and create path list
			if (visitednodes != null) {
			return bfs.pathCreation(goal, start, visitednodes);
			}
		else {
			System.out.println("Search was not successfull"); 
			return null;
		}
		}
	}
		
	
	/** 
	 * Find the path from start to goal using Dijkstra's algorithm
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
		
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** 
	 * Find the path from start to goal using Dijkstra's algorithm
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		Searcher dijkstraSearch = new DijkstraSearcher (start, goal, this.nodes);
		HashMap<GeographicPoint,GeographicPoint> visitednodes = dijkstraSearch.startSearch(null);
		
		System.out.println("Start Dijkstra Search...");
		//System.out.println("Check if parent map was returned correctly");
		for (GeographicPoint point : visitednodes.keySet()) {
			if (point == null || visitednodes.get(point) == null) {
				throw new NullPointerException ("Node in map is null");
			}
		//	System.out.println(point);
		}
		
		if (visitednodes != null && goal != null && start != null) {
			System.out.println("Number of nodes visited: " + visitednodes.size());
			return dijkstraSearch.pathCreation(goal, start, visitednodes);
			}
		else {
			System.out.println("Search was not successfull"); 
			return null;
		}
	}

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
	

	/** 
	 * Find the path from start to goal using A-Star search
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		Searcher AStarSearch = new AStarSearcher (start, goal, this.nodes);
		HashMap<GeographicPoint,GeographicPoint> visitednodes = AStarSearch.startSearch(null);
		
		System.out.println("Start A Star Search...");
		//System.out.println("Check if parent map was returned correctly");
		for (GeographicPoint point : visitednodes.keySet()) {
			if (point == null || visitednodes.get(point) == null) {
				throw new NullPointerException ("Node in map is null");
			}
		//	System.out.println(point);
		}
		
		if (visitednodes != null && goal != null && start != null) {
			System.out.println("Number of nodes visited: " + visitednodes.size());
			return AStarSearch.pathCreation(goal, start, visitednodes);
			}
		else {
			System.out.println("Search was not successfull"); 
			return null;
		}
	}

	
		
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		
		
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);

		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		
		//System.out.println("Number of nodes visited for dijkstra search: " + route.size());
		//System.out.println("Number of nodes visited for AStar search: " + route2.size());
		
		//Testing purpose
		//System.out.println("Test nodes map:");
		//for (GeographicPoint gp : firstMap.nodes.keySet()) {
		//	System.out.print("Node: ");
		//	System.out.print(gp + ", ");
		//	System.out.println ("Neighbors:");
		//	for (Intersection i : firstMap.nodes.get(gp).getNeighbors()) {
		//		System.out.println(" " + i.getGeographicPoint() + ",");
		//	}
		//}
		
		
		//System.out.println("Check number of neighbors");
		//	int numofneighbors = 0;
		//	int numofneighborsmap = 0;
		//	for (GeographicPoint gp : firstMap.nodes.keySet()) {
		//		numofneighbors += firstMap.nodes.get(gp).getNumNeighbors();
		//		numofneighborsmap += firstMap.nodes.get(gp).getNumNeighborsMap();
		//	}
		//System.out.println("Number of first neighbors variable: " + numofneighbors);	
		//System.out.println("Number of second neighbors variable: " + numofneighborsmap);	
		
		
		
		System.out.println("DONE.");
	
		
	
		// You can use this method for testing.  
		
	
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		
		//MapGraph simpleTestMap = new MapGraph();
		//GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		//GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		//GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		//System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		//List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		//List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		//List<GeographicPoint> testroute3 = simpleTestMap.bfs(testStart,testEnd);
		

		//System.out.println(testroute.size());
		
		
		/*
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
	}
}