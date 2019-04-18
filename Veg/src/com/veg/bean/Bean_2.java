package com.veg.bean;

public class Bean_2 {
	private String  num;
	private String name;
	private String pdate;
	private String sdate;
	private String edate;
	public String  getNum() {
		return num;
	}
	public void setNum(String  num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPdate() {
		return pdate;
	}
	public void setPdate(String pdate) {
		this.pdate = pdate;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	@Override
	public String toString() {
		return "Bean_2 [num=" + num + ", name=" + name + ", pdate=" + pdate + ", sdate=" + sdate + ", edate=" + edate
				+ "]";
	}
	public Bean_2(String  num, String name, String pdate, String sdate, String edate) {
		super();
		this.num = num;
		this.name = name;
		this.pdate = pdate;
		this.sdate = sdate;
		this.edate = edate;
	}
	public Bean_2() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
