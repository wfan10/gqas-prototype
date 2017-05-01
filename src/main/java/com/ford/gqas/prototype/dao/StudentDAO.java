package com.ford.gqas.prototype.dao;

import java.util.List;

import com.ford.gqas.prototype.model.StudentDTO;

public interface StudentDAO {

	public long getNextIdVal();
	
	public int createStudent( long id, String name, int age);

	public StudentDTO getStudent(long id);

	public List<StudentDTO> listStudent();

	public int deleteStudent( long id );

	public int updateStudent( long id, String name, int age);
}
