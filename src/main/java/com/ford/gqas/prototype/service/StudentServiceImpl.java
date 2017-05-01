package com.ford.gqas.prototype.service;

import java.util.List;
//import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.*;

import com.ford.gqas.prototype.dao.*;
import com.ford.gqas.prototype.model.*;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentDAO studentDao;

	@Autowired
	AddressDAO addressDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// private static volatile List<StudentDTO> students;

	public long getNextStudentId() {
		return studentDao.getNextIdVal();
	}

	/*
	 * TODO task details: find out how to cache this application wide object.
	 * private static volatile List<StateinfoDTO> states;
	 */
    @Cacheable(cacheNames = "states")
	public List<StateinfoDTO> listStates() {
    	
    	logger.info("listStates used addressDao.");
		return addressDao.listStates();
		
	}

	public List<StudentDTO> findAllStudents() {
		return studentDao.listStudent();
	}

	/*
	 * @Cacheable Place the StudentDTO into the cache "students" with key id
	 * @Cacheable( cacheNames="students", key="#id")
	 */
	@Cacheable(cacheNames = "students")
	public StudentDTO findStudentById(long id) {
		
		logger.info("findStudentById used studentDao.");
		StudentDTO student = studentDao.getStudent(id);
		return student;
	}

	/*
	 * @Cacheable Place the StudentDTO into a different cache with key being the
	 * name
	 * 
	 * @Cacheable(cacheNames = "studentnames", key = "#name")
	 * 
	 * public StudentDTO findStudentByName(String name) { // populate the list
	 * if empty. if (students == null || students.isEmpty()) populateStudents();
	 * 
	 * for (StudentDTO student : students) { if
	 * (student.getName().equalsIgnoreCase(name)) { return student; } } return
	 * null; }
	 */

	@Cacheable(cacheNames = "addresses")
	public AddressDTO getAddressById(long id) {
		
		logger.info("getAddressById used addressDao.");
		AddressDTO address = addressDao.getAddress(id);
		return address;
	}

	/*
	 * implement transaction for this method.(non-Javadoc)
	 * @Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class)
	 * Propagation.REQUIRED means any nested method call rollbacks will propagate to the parent
	 * For reference to @Transactional annotations see below. 
	 * tutorial: http://www.byteslounge.com/tutorials/spring-transaction-propagation-tutorial
	 * example: http://www.studytrails.com/frameworks/spring/spring-declarative-transactions-annotation/
	 * brief: http://stackoverflow.com/questions/10740021/transactionalpropagation-propagation-required
	 * detailed: http://docs.spring.io/spring/docs/2.5.x/reference/transaction.html
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveStudent(long id, StudentDTO student, AddressDTO address) {
		// use JdbcTemplate to save student to database.
		studentDao.createStudent(id, student.getName(), student.getAge());
		addressDao.createAddress(id, address.getStreet(), address.getCity(), address.getStateid());
	}

	
	@Transactional(propagation=Propagation.REQUIRED)
	@CacheEvict(cacheNames = "students", key = "#id")
	public void updateStudent(long id, StudentDTO student) {

		logger.info("updateStudent used studentDao.");
		String name = student.getName();
		int age = student.getAge();
		// use JdbcTemplate to update the student
		int status = studentDao.updateStudent(id, name, age);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@CacheEvict(cacheNames = { "students", "addresses" })
	public void deleteStudent(long id) {
		// use JdbcTemplate to delete the student
		logger.info("deleteStudent used addressDao.");
		
		studentDao.deleteStudent(id);
		addressDao.deleteAddress(id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@CacheEvict(cacheNames = "addresses", key = "#id")
	public void updateAddress(long id, AddressDTO address) {

		String street = address.getStreet();
		String city = address.getCity();
		int stateid = address.getStateid();
		
		// check to see if this object exist 
		AddressDTO curraddress = this.getAddressById(id);
		
		// do either insert or update. 
		if (curraddress == null) {
			int status = addressDao.createAddress(id, street, city, stateid);
			logger.info("updateAddress used addressDao.createAddress. status is " + status );
		} else {
			int status = addressDao.updateAddress(id, street, city, stateid);
			logger.info("updateAddress used addressDao.updateAddress. status is " + status );
		}
	}

	@Cacheable(cacheNames = "stateinfo")
	public StateinfoDTO getStateInfo(int stateid) {
		
		logger.info("getStateInfo used addressDao.");
		
		List<StateinfoDTO> states = addressDao.listStates();
		for (StateinfoDTO state : states) {
			if (state.getStateId() == stateid) 
				return state;
		}
		return null;
	}

}
