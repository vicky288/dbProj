package com.functionalities.borrowerMgmt;

public class Borrower {
	private int card_no;
	private String fname;
	private String lname;
	private String address;
	private String city;
	private String state;
	private String phone;
	public int getCard_no() {
		return card_no;
	}
	public void setCard_no(int card_no) {
		this.card_no = card_no;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
