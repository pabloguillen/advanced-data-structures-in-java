package roadgraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Consumer;

import geography.GeographicPoint;

public class DijkstraSearcher extends Searcher {
	
	//private PriorityQueue<Distances> pq;
	
	//private PriorityQueue<Intersection> pq;
	
	//ADD Priority Queue
	
	
	
	public DijkstraSearcher(GeographicPoint s, GeographicPoint g,HashMap<GeographicPoint,Intersection> vertices) {
		super(s, g, vertices);
		//pq = new PriorityQueue<Intersection> ();
		
	}
	
	public void parentmapCreation (Intersection currnode, List<GeographicPoint> visited, Double currdistance, 
									HashMap<Intersection, Intersection> parentmap, GeographicPoint goal) {
		
		for (Intersection n : currnode.getNeighbors()) {
			
			
			//Correct?
			if (!visited.contains(n.getGeographicPoint())) {
				
				//if path through n to curr is shorter
		
					//System.out.println(n.getGeographicPoint());
					
					//get distance to start
					//if (currnode == nodes.get(start)) {
					//	localdistance = 0.0;
					//} else {
			
					Double localdistance = n.getGeographicPoint().distance(currnode.getGeographicPoint());
					//}
					Double distanceToStart = currdistance + localdistance;
					
					
					
					
					//System.out.println("Distance: " + distanceToStart);		
					//AStar
					//Double distanceToEnd = currnode.getGeographicPoint().distance(goal);
		
		
			if (parentmap.containsKey(n)) {
				Double comparedist = n.getDistance();
				if (comparedist > distanceToStart) {
					n.setDistance(distanceToStart);
					parentmap.put(n, currnode);
				}
			} 
			else {
				n.setDistance(distanceToStart);
				parentmap.put(n, currnode);
			}
			addPq(n);
		}
	}
	}
	public HashMap<GeographicPoint, GeographicPoint> executeBFSSearch(HashMap<GeographicPoint,Intersection> nodes, GeographicPoint start, 
			GeographicPoint goal, List<GeographicPoint> visited, 
					HashMap<GeographicPoint,GeographicPoint> parent, 
					Consumer<GeographicPoint> nodeSearched) {
			HashMap<GeographicPoint, GeographicPoint> list = null;
			return list;
		}
}