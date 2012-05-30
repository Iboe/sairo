package de.fhb.sailboat.data;

/**
 * Data object representing a point on the map, containing a GPS coordinate,
 * a radius indicating the dimension and the type of the point. 
 * 
 * @author hscheel
 *
 */
public class MapPoint {

	private final GPS position;
	private final double radius;
	private final MapPointType type;
	
	/**
	 * Constructs a new instance with the values handed over.
	 * 
	 * @param type the value for type
	 * @param position the value for position
	 * @param radius the value for radius
	 */
	public MapPoint(MapPointType type, GPS position, double radius) {
		this.type = type;
		this.position = position;
		this.radius = radius;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapPoint other = (MapPoint) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (Double.doubleToLongBits(radius) != Double
				.doubleToLongBits(other.radius))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public GPS getPosition() {
		return position;
	}

	public double getRadius() {
		return radius;
	}

	public MapPointType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
}
