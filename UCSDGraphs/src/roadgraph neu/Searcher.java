package roadgraph;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.HashSet;
import java.util.LinkedList;

import geography.GeographicPoint;

public abstract class Searcher {
	
	private GeographicPoint start;
	private GeographicPoint goal;	
	private List<GeographicPoint> visited;
	private HashMap<GeographicPoint,GeographicPoint> parent;
	private HashMap<GeographicPoint,Intersection> nodes;
	private PriorityQueue<Intersection> pq;
	
	
	public Searcher (GeographicPoint s, GeographicPoint g, HashMap<GeographicPoint,Intersection> vertices) {
		start = s;
		goal = g;
		visited = new LinkedList<GeographicPoint>();
		parent = new HashMap<GeographicPoint,GeographicPoint> ();
		nodes = vertices;	
		pq = new PriorityQueue<Intersection> ();
	}
	
	
	public boolean checkPreconditions () {
		if (start == null || goal == null) {
			throw new NullPointerException("Cannot find route from or to null node");
		}	
		else if (nodes.get(start) == null) {
				System.err.println("Start node " + start + " does not exist");
				return false;
		}
		else if (nodes.get(goal) == null) {
			System.err.println("End node " + goal + " does not exist");
			return false;
		}
		else {
			return true;
		}
	}
	
	/** 
	 * Creation of the path resulting from bfs search
	 * @param goal The goal location
	 * @param start The start location
	 * @param visitednodes HashMap which maps the nodes and their parent nodes
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public  List<GeographicPoint> pathCreation (GeographicPoint goal, GeographicPoint start, HashMap<GeographicPoint,GeographicPoint> visitednodes){
		
		LinkedList<GeographicPoint> result = new LinkedList<GeographicPoint>();
		
		GeographicPoint current = goal;
		
		//System.out.println ("Goal: " + goal);
		//System.out.println ("Start: " + start);
	
		
		while (current != start && current != null) {
			result.addFirst(current);
			
			GeographicPoint currparent = visitednodes.get(current);
			
			current = currparent;
			
		}
		
		
		//System.out.println("Check Path:");
		
		//for (GeographicPoint gp : result) {
		//	System.out.println(gp);
		//}
		
		return result;
		
	}
	
	
	/** 
	 * Initiating bfs search
	 * @param nodeSearched as hook for visualization
	 * @return
	 */
	public HashMap<GeographicPoint,GeographicPoint> startSearch (Consumer<GeographicPoint> nodeSearched) {
		return executeSearch (nodes, start,goal,visited,parent,nodeSearched);
	}
	
	public HashMap<GeographicPoint,GeographicPoint> startBFSSearch (Consumer<GeographicPoint> nodeSearched) {
		return executeBFSSearch (nodes, start,goal,visited,parent,nodeSearched);
	}
	
	
	public abstract HashMap<GeographicPoint, GeographicPoint> executeBFSSearch(HashMap<GeographicPoint,Intersection> nodes, GeographicPoint start, 
			GeographicPoint goal, List<GeographicPoint> visited, 
			HashMap<GeographicPoint,GeographicPoint> parent, 
			Consumer<GeographicPoint> nodeSearched);
	
	/** 
	 * Execution of bfs search
	 * @param visited LinkedList which keeps track of visited nodes during bfs search
	 * @param nodes as vertices of graph
	 * @param parent HashMap which maps the nodes and their respective parent node
	 * @param start The start location
	 * @param goal The goal location
	 * @param nodeSearched hook for visualization
	 * @return
	 */
	protected HashMap<GeographicPoint,GeographicPoint> executeSearch (HashMap<GeographicPoint,Intersection> nodes, 
																			GeographicPoint start, GeographicPoint goal, List<GeographicPoint> visited, 
																			HashMap<GeographicPoint, GeographicPoint> parent, Consumer<GeographicPoint> nodeSearched){

		HashMap<Intersection, Intersection> parentmap = new HashMap<Intersection, Intersection> ();
				
		
		//for (GeographicPoint gp : nodes.keySet()) {
			
		//saving memory potential
			
		//	HashSet<Intersection> parents = new HashSet<Intersection> ();
		//	parentmap.put(nodes.get(gp), parents);
		//}
		
		//Set all nodes to infinite distance (for multiple searches)
		for (GeographicPoint gp : nodes.keySet()) {
			nodes.get(gp).setDistance(Double.POSITIVE_INFINITY);
		}
		
		
		//Initialization of variables
		Intersection startnode = nodes.get(start);
		Intersection goalnode = nodes.get(goal);
		Double currdistance = 0.0;
		Double currEstimatedDistance = 0.0;
		Double localdistance;
		Intersection currnode = null;
		startnode.setDistance(0.0);
		startnode.setEstimatedDistance(start.distance(goal));
		pq.add(startnode);
		
		
		//System.out.println("Following nodes are visited:");
		
		//System.out.println("\n");
		
		
		
		
		
		while (!pq.isEmpty()) {
			
			//Deque Intersection node with highest priority
			currnode = pq.remove();
			
			//Update currdistance to start
			currdistance = currnode.getDistance();
			
			//Update currEstimatedDistance to start
			currEstimatedDistance = currnode.getEstimatedDistance();
			
			//System.out.println("\n");
			//System.out.println("Current node:" + currnode.getGeographicPoint());
			
			if (!visited.contains(currnode.getGeographicPoint())) {
				visited.add(currnode.getGeographicPoint());
			}
			if (currnode.equals(goalnode)) {
				break;
			}
			else {
				
				//System.out.println("\n");
				//System.out.println("Visited node:" + currnode.getGeographicPoint());
				//System.out.println("\n");
				//System.out.println("Visited neighbors:");
				
				parentmapCreation (currnode, visited, currdistance, parentmap, goal);
				
				}
			
			
			}
		
		if (!currnode.equals(goalnode)) {
			System.out.println("No path found from " + start + " to " + goal);
			return null;
		}
		else {
			
		//Deleting objects in the heap through Garbage cleaner (GC)	
		startnode = null;
		goalnode = null;
		currdistance = null;
		localdistance = null;
		currnode = null;	
		return parentmapTranslation (parentmap);
		
		}
	}
	
	
	public void addPq (Intersection n) {
		this.pq.add(n);
	}
	
	
	public GeographicPoint getStart () {
		return start;
	}
	
	public GeographicPoint getGoal () {
		return goal;
	}
	
	public List<GeographicPoint> getVisted (){
		return visited;
	}
	
	public HashMap<GeographicPoint,GeographicPoint> getParent (){
		return parent;
	}
	
	public abstract void parentmapCreation (Intersection currnode, List<GeographicPoint> visited, Double currdistance, 
			HashMap<Intersection, Intersection> parentmap, GeographicPoint goal);
	
	private HashMap<GeographicPoint,GeographicPoint> parentmapTranslation (HashMap<Intersection, Intersection> parentmap){
		
	HashMap<GeographicPoint,GeographicPoint> parentlist = new HashMap<GeographicPoint,GeographicPoint>();
	
	for (Intersection n : parentmap.keySet()) {
		//System.out.println("Currnode: " + node.getGeographicPoint());
		//System.out.println("Parents: " + pn.getGeographicPoint());
		parentlist.put(n.getGeographicPoint(),parentmap.get(n).getGeographicPoint());
	}

	
	return parentlist;
	}
}

	

