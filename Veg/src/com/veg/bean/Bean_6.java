package com.veg.bean;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders={"num","name","ml","date"})
public class Bean_6 {
	private String num;
	private String name;
	private int ml;
	private String date;
	public String getNum() {
		return num;
	}
	@Override
	public String toString() {
		return "Bean_6 [num=" + num + ", name=" + name + ", ml=" + ml + ", date=" + date + "]";
	}
	public Bean_6() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bean_6(String num, String name, int ml, String date) {
		super();
		this.num = num;
		this.name = name;
		this.ml = ml;
		this.date = date;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMl() {
		return ml;
	}
	public void setMl(int ml) {
		this.ml = ml;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}