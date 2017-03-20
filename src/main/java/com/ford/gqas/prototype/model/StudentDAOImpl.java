package com.ford.gqas.prototype.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAOImpl implements StudentDAO {

	// @Autowired
	// private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplateObj;

	private static final AtomicLong counter = new AtomicLong();

	/*
	 * public void setDataSource(DataSource ds) { this.dataSource = ds;
	 * this.jdbcTemplateObj = new JdbcTemplate( dataSource ); }
	 */

	public int create(String name, int age) {
		String SQL = "INSERT INTO Student (id,name,age) values (?,?,?)";
		long id = counter.incrementAndGet();
		int status = jdbcTemplateObj.update(SQL, id, name, age);
		if ( status == 0 ) System.out.println("Created Student named " + name);
		return status;
	}

	public StudentDTO getStudent( long id ) {
		String SQL = "SELECT * FROM Student WHERE id = ?";
		StudentDTO student = jdbcTemplateObj.queryForObject(SQL, new Object[] { id }, new StudentMapper());
		return student;
	}

	public List<StudentDTO> listStudent() {
		String SQL = "SELECT * FROM Student";
		List<StudentDTO> students = jdbcTemplateObj.query(SQL, new StudentMapper());
		return students;
	}

	public int delete( long id) {
		String SQL = "DELETE FROM Student WHERE id = ?";
		int status = jdbcTemplateObj.update(SQL, id);
		if ( status == 0 ) System.out.println("Deleted student id " + id);
		return status;
	}

	public int update( long id, int age) {
		String SQL = "UPDATE student SET age = ? WHERE id = ?";
		int status = jdbcTemplateObj.update(SQL, age, id);
		if ( status == 0 ) System.out.println("Updated student with id " + id);
		return status;
	}

}
