package com.ford.gqas.prototype.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ford.gqas.prototype.model.*;

// Note this class as well as any utility classes are created as singleton beans. 
// That is only one instance per spring container.  Note that there should be no state variables inside this class. 
// The list of students is a cache lookup of students. 
// However, it is refreshed in every sql transaction ... not a good way to do a cache.
// Cache lookup objects should be stable and not change with each request. Do not try this at home ... this is just a demo.
// Below annotations are not necessary ... singleton beans are the default
// @Scope("singleton") or @Scope("application")
@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentDAO studentDao;

	private static volatile List<StudentDTO> students;
	
	private void populateStudents() {
		// delete the list and then use JdbcTemplate to fetch all students.
		//students.clear();
		// students = studentDao.listStudentDTO();
		//populateDummyUsers();
		students = studentDao.listStudent();
	}

	/*
	 * private void populateDummyUsers(){ //students.clear(); students.add( new
	 * StudentDTO( 1,"Sam",30 )); students.add( new StudentDTO( 2,"Tom",40 ));
	 * students.add( new StudentDTO( 3,"Jerome",45 )); students.add( new
	 * StudentDTO( 4,"Silvia",50 )); }
	 */

	public StudentDTO findById(long id) {
		// populate the list if empty.
		if ( students == null || students.isEmpty())
			populateStudents();

		for (StudentDTO student : students) {
			if (student.getId() == id) {
				return student;
			}
		}
		return null;
	}

	public StudentDTO findByName(String name) {
		// populate the list if empty.
		if (students == null || students.isEmpty())
			populateStudents();

		for (StudentDTO student : students) {
			if (student.getName().equalsIgnoreCase(name)) {
				return student;
			}
		}
		return null;
	}

	public void saveStudent(StudentDTO student) {
		// use JdbcTemplate to save student to database.
		// save to database and then refresh the list again.
		int status = studentDao.create(student.getName(), student.getAge());
		// refresh the list.
		if (status > 0)
			students.clear();
	}

	public void updateStudent(StudentDTO student) {
		//
		long id = student.getId();
		int age = student.getAge();
		// use JdbcTemplate to update the student
		int status = studentDao.update(id, age);
		// refresh the list again
		if (status > 0)
			students.clear();
	}

	public void deleteStudent(long id) {
		// use JdbcTemplate to delete the student
		int status = studentDao.delete(id);
		// refresh the list again
		if (status > 0)
			students.clear();
	}

	public List<StudentDTO> findAllStudents() {
		// populate the list if empty.
		if (students == null || students.isEmpty())
			populateStudents();
		return students;
	}

	public boolean isStudentExist(StudentDTO student) {
		StudentDTO stdent = findByName(student.getName());
		if (stdent == null)
			return false;
		return true;
	}

}
