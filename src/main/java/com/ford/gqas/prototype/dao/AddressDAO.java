package com.ford.gqas.prototype.dao;

import java.util.List;

import com.ford.gqas.prototype.model.*;

public interface AddressDAO {
	
	public List<StateinfoDTO> listStates();

	public int createAddress(long id, String street, String city, int stateid);

	public AddressDTO getAddress(long id);

	public int deleteAddress(long id);

	public int updateAddress(long id, String street, String city, int stateid);
	
}
