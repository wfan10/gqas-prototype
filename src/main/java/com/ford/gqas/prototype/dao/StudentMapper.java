package com.ford.gqas.prototype.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ford.gqas.prototype.model.StudentDTO;

public class StudentMapper implements RowMapper<StudentDTO>{

	public StudentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		StudentDTO student = new StudentDTO();
		student.setId( rs.getInt("id"));
		student.setName( rs.getString("name"));
		student.setAge( rs.getInt("age"));
		return student;
	}
	
}