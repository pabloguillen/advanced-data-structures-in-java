package roadgraph;


import geography.GeographicPoint;

public class Road {
	//Name of the road
	private String name;

	//Type of the road
	private String type;

	//Length of the road 
	private double length;

	//Start location of the road
	private GeographicPoint start;

	//End location of the road
	private GeographicPoint end;

	public Road (GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double roadLength) {
		
		name = roadName;
		type = roadType;
		length = roadLength;
		start = from;
		end = to;
	}

	/**
	 * Get the name of road
	 * @return Name of the road of type String
	 */
	public String getName () {
		return name;
	}

	/**
	 * Get the type of road
	 * @return Type of the road of type String
	 */
	public String getType () {
		return type;
	}

	/**
	 * Get the length of road
	 * @return Length of the road of type double
	 */
	public double getLength () {
		return length;
	}

	/**
	 * Get the start location of road
	 * @return Start location of the road of type GeographicPoint
	 */
	public GeographicPoint getStart () {
		return start;
	}

	/**
	 * Get the end location of road
	 * @return End location of the road of type GeographicPoint
	 */
	public GeographicPoint getEnd () {
		return end;
	}
}
