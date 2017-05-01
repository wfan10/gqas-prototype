package com.ford.gqas.prototype.model;

import java.io.Serializable;

public class StateinfoDTO implements Serializable{
	
	private int stateId;
	private String stateName;
	private String stateNym;
	
	public StateinfoDTO(){}
	
	public StateinfoDTO( int stateId, String stateName, String stateNym ){
		this.stateId = stateId;
		this.stateName = stateName;
		this.stateNym = stateNym;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateNym() {
		return stateNym;
	}

	public void setStateNym(String stateNym) {
		this.stateNym = stateNym;
	}	
	
}
