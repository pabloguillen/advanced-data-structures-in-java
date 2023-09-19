package roadgraph;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Consumer;

import geography.GeographicPoint;

public class AStarSearcher extends Searcher {
	
	public AStarSearcher (GeographicPoint s, GeographicPoint g,HashMap<GeographicPoint,Intersection> vertices) {
	
	super(s, g, vertices);
	
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
					
					Double total = distanceToStart + n.getGeographicPoint().distance(goal);
					
							
					//AStar
					//Double distanceToEnd = currnode.getGeographicPoint().distance(goal);
		
		
			if (parentmap.containsKey(n)) {
				Double comparedist = n.getEstimatedDistance();
				//System.out.println("Current estimated Distance updated: " + total);
				if (comparedist > total) {
					n.setEstimatedDistance(total);
					n.setDistance(distanceToStart);
					parentmap.put(n, currnode);
					//System.out.println("Estimated Distance updated: " + total);
				}
			} 
			else {
				n.setEstimatedDistance(total);
				n.setDistance(distanceToStart);
				//System.out.println("Added with estimaed Distance updated: " + total);
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


