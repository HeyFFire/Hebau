package bean;

import java.util.Arrays;

public class Geometry {
	private double []coordinates;
	private String type;
	public double[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "geometry [coordinates=" + Arrays.toString(coordinates)
				+ ", type=" + type + "]";
	}
	public Geometry() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Geometry(double[] coordinates, String type) {
		super();
		this.coordinates = coordinates;
		this.type = type;
	}
	

}
