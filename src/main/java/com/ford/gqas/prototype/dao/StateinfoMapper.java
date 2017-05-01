package com.ford.gqas.prototype.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.ford.gqas.prototype.model.StateinfoDTO;

public class StateinfoMapper implements RowMapper<StateinfoDTO>{

	public StateinfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		StateinfoDTO state  = new StateinfoDTO();
		state.setStateId( rs.getInt("stateid"));
		state.setStateName( rs.getString("statename"));
		state.setStateNym( rs.getString("statenym"));
		return state;
	}	
	
}