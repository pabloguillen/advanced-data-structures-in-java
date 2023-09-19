package roadgraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

import geography.GeographicPoint;

public class BFSSearch extends Searcher {
	
	private Queue<GeographicPoint> toVisit;

	public BFSSearch (GeographicPoint s, GeographicPoint g, HashMap<GeographicPoint,Intersection> vertices) {
		super(s, g, vertices);
		toVisit = new LinkedList<GeographicPoint> ();
	}

	@Override
	public List<GeographicPoint> pathCreation(GeographicPoint goal, GeographicPoint start, HashMap<GeographicPoint,GeographicPoint> visitednodes) {
		// TODO Auto-generated method stub
		LinkedList<GeographicPoint> result = new LinkedList<GeographicPoint>();
		GeographicPoint curr = goal;
		while (curr != start) {
			result.addFirst(curr);
			GeographicPoint currparent = visitednodes.get(curr);
			curr = currparent;
		}
		
		result.addFirst(start);

		return result;
	}


	public HashMap<GeographicPoint, GeographicPoint> executeBFSSearch(HashMap<GeographicPoint,Intersection> nodes, GeographicPoint start, 
															GeographicPoint goal, List<GeographicPoint> visited, 
																	HashMap<GeographicPoint,GeographicPoint> parent, 
																	Consumer<GeographicPoint> nodeSearched) {
		
		toVisit.add(start);
		visited.add(start);
		GeographicPoint curr = null;

		while (!toVisit.isEmpty()) {
			
			curr = toVisit.remove();
			
			//hook for visualization
			nodeSearched.accept(curr);
			
			if (curr.equals(goal)) {
				break;
			}
			else {
				
		for (Intersection n : nodes.get(curr).getNeighbors()) {
			
				if (!visited.contains(n.getGeographicPoint())) {
					
					visited.add(n.getGeographicPoint());
					
					parent.put(n.getGeographicPoint(), curr);
					
					toVisit.add(n.getGeographicPoint());

					}
				}
			}
		}
		
		//check if path was found
		if (!curr.equals(goal)) {
			System.out.println("No path found from " + start + " to " + goal);
			return null;
		}
		else {
		return parent;
	 }
	}
	
	public void parentmapCreation (Intersection currnode, List<GeographicPoint> visited, Double currdistance, 
			HashMap<Intersection, Intersection> parentmap, GeographicPoint goal) {
	
	}
}
