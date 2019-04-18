package com.veg.bean;

import com.alibaba.fastjson.annotation.JSONType;
@JSONType(orders={"num","water","date"})
public class Bean_1 {
	private String num;
	private String water;
	private String date;
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getWater() {
		return water;
	}
	public void setWater(String water) {
		this.water = water;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Bean_1(String num, String water, String date) {
		super();
		this.num = num;
		this.water = water;
		this.date = date;
	}
	public Bean_1() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Bean_1 [num=" + num + ", water=" + water + ", date=" + date + "]";
	}

}
