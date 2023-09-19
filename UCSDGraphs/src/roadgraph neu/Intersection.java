package roadgraph;

import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

import geography.GeographicPoint;

/**
 * @author User
 * 
 * A class which represents a node within a map representing an intersection between streets
 * An Intersection is located at a certain geographical point.
 *
 */

public class Intersection implements Comparable<Intersection> {
	
	//location of respective node (GeographicPoint)
	private GeographicPoint point;
	
	//Set of neighbors (Intersection)
	private Set<Intersection> neighbors;
	
	
	private HashMap<Intersection, Road> streets;
	
	//private Distances distances;
	
	private Double distance;
	
	private Double estimateddistance;
	
	public Intersection (GeographicPoint pt) {
		
		point = pt;
		neighbors = new HashSet<Intersection>();
		//distances = new Distances ();
		distance = Double.POSITIVE_INFINITY;
		estimateddistance = Double.POSITIVE_INFINITY;
		streets =new HashMap<Intersection, Road>();
	}
	
	/** 
	 * Get the neighbors of the intersection
	 * @return Set of Intersection
	 */
	
	public Set<Intersection> getNeighbors (){
		return neighbors;
	}
	
	/**
	 * Get the number of neighbors
	 * @return the number of neighbors (int)
	 */
	public int getNumNeighbors() {
		return neighbors.size();
	}
	
	//Testing Purpose
	public int getNumNeighborsMap () {
		return streets.size();
	}
	
	/**
	 * Get the location of the Intersection
	 * @return GeographicPoint of the Intersection
	 */
	public GeographicPoint getGeographicPoint () {
		return point;
	}
	
	/**
	 * Add a neighbor to the intersection
	 * @param void
	 */
	public void addNeighbor(Intersection p, Road r) {
		neighbors.add(p);
		streets.put(p, r);
	}

	public Double getDistance() {
		return distance;
	}
	
	public void setDistance (Double value) {
		this.distance = value;
	}
	
	public int compareTo (Intersection x) {
		if (this.distance > x.distance) {
			return 1;
		} 
		else if (this.distance < x.distance) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	public void setEstimatedDistance (Double value) {
		this.estimateddistance = value;
	}
	
	public Double getEstimatedDistance () {
		return estimateddistance;
	}
}
