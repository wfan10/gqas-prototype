package com.ford.gqas.prototype.service;

import java.util.List;
import com.ford.gqas.prototype.model.StudentDTO;

public interface StudentService {
	
	StudentDTO findById( long id );
	StudentDTO findByName( String name );
	void saveStudent( StudentDTO student );
	void updateStudent( StudentDTO student );
	void deleteStudent( long id );
	List<StudentDTO> findAllStudents();
	public boolean isStudentExist( StudentDTO student );
}