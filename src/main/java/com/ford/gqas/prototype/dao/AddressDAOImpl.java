package com.ford.gqas.prototype.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ford.gqas.prototype.model.AddressDTO;
import com.ford.gqas.prototype.model.StateinfoDTO;

@Repository
public class AddressDAOImpl implements AddressDAO {

	@Autowired
	private JdbcTemplate jdbcTemplateObj;
	
	//@Autowired
	//private NamedParameterJdbcTemplate namedTemplateObj;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<StateinfoDTO> listStates() {
		String sql = "SELECT * FROM stateinfo";
		List<StateinfoDTO> states = jdbcTemplateObj.query(sql, new StateinfoMapper());
		return states;
	}

	public int createAddress(long id, String street, String city, int stateid) {
		String sql = "INSERT INTO address (id, street, city, stateid ) VALUES ( ?,?,?,? )";
		int status = jdbcTemplateObj.update(sql, id, street, city, stateid);
		if (status != 0)
			logger.error("createAddress failed for id " + id);
		return status;
	}

	public AddressDTO getAddress(long id) {
		String sql = "SELECT * FROM address WHERE id = ?";
		AddressDTO address = jdbcTemplateObj.queryForObject(sql, new Object[] { id }, new AddressMapper());
		return address;
	}

	public int deleteAddress(long id) {
		String sql = "DELETE FROM address WHERE id = ?";
		int status = jdbcTemplateObj.update(sql, id);
		if (status != 0)
			logger.error("deleteAddress failed for id " + id);
		return status;
	}

	public int updateAddress(long id, String street, String city, int stateid) {
		String sql = "UPDATE address SET street = ?, city = ?, stateid = ? WHERE id = ?";
		int status = jdbcTemplateObj.update(sql, street, city, stateid, id);
		if (status != 0)
			logger.error("updateAddress failed with id " + id);
		return status;
	}

}
