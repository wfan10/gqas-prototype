package com.ford.gqas.prototype.service;

import java.util.List;
import com.ford.gqas.prototype.model.*;

public interface StudentService {
	
	// return a list of students
	List<StudentDTO> findAllStudents();
	
	// Return student by id or name
	StudentDTO findStudentById( long id );
	
	// Return the next id from sequence
	long getNextStudentId();
	
	// use session and transaction to save student and address
	void saveStudent( long id, StudentDTO student, AddressDTO address );
	
	// use transaction to delete student and address
	void deleteStudent( long id );
	
	// update student only
	void updateStudent( long id, StudentDTO student );
	
	// Return address by id 
	AddressDTO getAddressById( long id );
	
	// update student address only
	void updateAddress( long id, AddressDTO address );

	// return a state lookup table
	List<StateinfoDTO> listStates();
	
	// return a state info object by id
	StateinfoDTO getStateInfo( int stateid );
}