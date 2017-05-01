package com.ford.gqas.prototype.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ford.gqas.prototype.model.AddressDTO;

public class AddressMapper implements RowMapper<AddressDTO>{

	public AddressDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		AddressDTO address = new AddressDTO();
		address.setId( rs.getLong("id"));
		address.setStreet( rs.getString("street"));
		address.setCity( rs.getString("city"));
		address.setStateid( rs.getInt("stateid"));
		return address;
	}	
}