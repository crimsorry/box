package com.yuhan.test.dto;

import java.util.Map;

public class PagingDTO {

	// 페이징
	private Map<String, Integer> p;
	private String btn;
	private String debtn;
	private String location;
	
	//
	private Map<String, String> p2;
	private String msg;
	private String seemsg;
	private String num;
	private String box_code;
	private String startDate;
	private String endDate;
	private int totalError;
	private int badLine;
	
	public int getTotalError() {
		return totalError;
	}
	public void setTotalError(int totalError) {
		this.totalError = totalError;
	}
	public int getBadLine() {
		return badLine;
	}
	public void setBadLine(int badLine) {
		this.badLine = badLine;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBox_code() {
		return box_code;
	}
	public void setBox_code(String box_code) {
		this.box_code = box_code;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSeemsg() {
		return seemsg;
	}
	public void setSeemsg(String seemsg) {
		this.seemsg = seemsg;
	}
	public Map<String, String> getP2() {
		return p2;
	}
	public void setP2(Map<String, String> p2) {
		this.p2 = p2;
	}
	public String getDebtn() {
		return debtn;
	}
	public void setDebtn(String debtn) {
		this.debtn = debtn;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBtn() {
		return btn;
	}
	public void setBtn(String btn) {
		this.btn = btn;
	}
	public Map<String, Integer> getP() {
		return p;
	}
	public void setP(Map<String, Integer> p) {
		this.p = p;
	}
	
}
