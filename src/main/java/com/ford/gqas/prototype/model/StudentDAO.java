package com.ford.gqas.prototype.model;

import java.util.List;
import javax.sql.DataSource;

public interface StudentDAO {

	public int create(String name, int age);

	public StudentDTO getStudent(long id);

	public List<StudentDTO> listStudent();

	public int delete(long id);

	public int update(long id, int age);
}
