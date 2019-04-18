package bean;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders={"num","province","city","county","village","x","y","ID","square","greenhouse","main","date"})
public class Details {
	private String num;
	private double x;
	private double y;
	private String ID;
	private double square;
	private String province;
	private String city;
	private String county;
	private String village;
	private String greenhouse;
	private String main;
	private String date;
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public double getSquare() {
		return square;
	}
	public void setSquare(double square) {
		this.square = square;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getGreenhouse() {
		return greenhouse;
	}
	public void setGreenhouse(String greenhouse) {
		this.greenhouse = greenhouse;
	}
	public String getMain() {
		return main;
	}
	public void setMain(String main) {
		this.main = main;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Bean [num=" + num + ", x=" + x + ", y=" + y + ", ID=" + ID + ", square=" + square + ", province="
				+ province + ", city=" + city + ", county=" + county + ", village=" + village + ", greenhouse="
				+ greenhouse + ", main=" + main + ", date=" + date + "]";
	}
	public Details() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Details(String num, double x, double y, String iD, double square, String province, String city, String county,
			String village, String greenhouse, String main, String date) {
		super();
		this.num = num;
		this.x = x;
		this.y = y;
		ID = iD;
		this.square = square;
		this.province = province;
		this.city = city;
		this.county = county;
		this.village = village;
		this.greenhouse = greenhouse;
		this.main = main;
		this.date = date;
	}
	
}
