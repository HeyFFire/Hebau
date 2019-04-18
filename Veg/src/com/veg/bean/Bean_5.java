package com.veg.bean;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders={"num","name","ml","date"})
public class Bean_5 {
	private String num;
	private String name;
	private int ml;
	@Override
	public String toString() {
		return "Bean_4 [num=" + num + ", name=" + name + ", ml=" + ml + ", date=" + date + "]";
	}
	public Bean_5() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bean_5(String num, String name, int ml, String date) {
		super();
		this.num = num;
		this.name = name;
		this.ml = ml;
		this.date = date;
	}
	public String getNum() {
		return num;
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
	private String date;
}
