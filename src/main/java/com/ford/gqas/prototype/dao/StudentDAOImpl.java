package com.ford.gqas.prototype.dao;

import java.util.List;
//import java.util.concurrent.atomic.AtomicLong;

//import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.*;

import com.ford.gqas.prototype.model.StudentDTO;

@Repository
public class StudentDAOImpl implements StudentDAO {

	@Autowired
	private JdbcTemplate jdbcTemplateObj;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public long getNextIdVal() {
		String sql = "SELECT STUDENT_ID_SEQ.NEXTVAL FROM DUAL";
		long idval = jdbcTemplateObj.queryForObject(sql, Long.class);
		return idval;
	}

	public int createStudent(long id, String name, int age) {
		String sql = "INSERT INTO student (id,name,age) values (?,?,?)";
		int status = jdbcTemplateObj.update(sql, id, name, age);
		if (status < 1) 
			logger.error("createStudent failed for id " + id);
		return status;
	}

	public StudentDTO getStudent(long id) {
		String sql = "SELECT * FROM Student WHERE id = ?";
		StudentDTO student = jdbcTemplateObj.queryForObject(sql, new Object[] { id }, new StudentMapper());
		return student;
	}

	public List<StudentDTO> listStudent() {
		String sql = "SELECT * FROM Student";
		List<StudentDTO> students = jdbcTemplateObj.query(sql, new StudentMapper());
		return students;
	}

	public int deleteStudent(long id) {
		String sql = "DELETE FROM student WHERE id = ?";
		int status = jdbcTemplateObj.update(sql, id);
		if (status < 1) 
			logger.error("deleteStudent failed for id " + id);
		return status;
	}

	public int updateStudent(long id, String name, int age) {
		String sql = "UPDATE student SET name = ?, age = ? WHERE id = ?";
		int status = jdbcTemplateObj.update(sql, name, age, id);
		if (status < 1)
			logger.error("updateStudent failed with id " + id);
		return status;
	}

}
