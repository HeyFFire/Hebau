package bean;
import com.alibaba.fastjson.annotation.JSONType;

import bean.Geometry;
import bean.Properties;
@JSONType(orders={"properties","geometry","type"})
public class Features {
	private Geometry geometry;
	private Properties properties;
	private String type;
	@Override
	public String toString() {
		return "Features [geometry=" + geometry + ", properties=" + properties
				+ ", type=" + type + "]";
	}
	public Features() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Features(Geometry geometry, Properties properties, String type) {
		super();
		this.geometry = geometry;
		this.properties = properties;
		this.type = type;
	}
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties2) {
		this.properties = properties2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
