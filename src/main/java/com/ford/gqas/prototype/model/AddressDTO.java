package com.ford.gqas.prototype.model;

import java.io.Serializable;

public class AddressDTO implements Serializable{
	private long id;
	private String street;
	private String city;
	private int stateid;

	public AddressDTO() {
	}

	public AddressDTO(long id, String street, String city, int state_id) {
		this.id = id;
		this.street = street;
		this.city = city;
		this.stateid = state_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getStateid() {
		return stateid;
	}

	public void setStateid(int stateid) {
		this.stateid = stateid;
	}

}
