package com.ford.gqas.prototype.controller;

import java.util.HashMap;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.*;

import com.ford.gqas.prototype.service.StudentService;
import com.ford.gqas.prototype.model.*;

// This way works to allow query from any domain origin. 
@CrossOrigin
// @CrossOrigin(origins = "http://localhost:8081")
@RestController
public class StudentController {

	@Autowired
	StudentService studentService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public Map<String, String> getResource() {
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "here is some resource");
		return resource;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String getTest() {
		String resource = "Hello admin";
		return resource;
	}

	// invalidate session
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<Void> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	// get all states
	@RequestMapping(value = "/states", method = RequestMethod.GET)
	public ResponseEntity<List<StateinfoDTO>> listAllStates() {
		
		logger.info("listAllStates: entered.");
		
		List<StateinfoDTO> states = studentService.listStates();
		
		logger.info("listAllStates: studentService returned states."  );
		
		if (states.isEmpty()) {
			// You many decide to return HttpStatus.NOT_FOUND
			return new ResponseEntity<List<StateinfoDTO>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<StateinfoDTO>>(states, HttpStatus.OK);
	}

	// get all students
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public ResponseEntity<List<StudentDTO>> listAllStudents() {

		List<StudentDTO> students = studentService.findAllStudents();

		if (students.isEmpty()) {
			// You many decide to return HttpStatus.NOT_FOUND
			return new ResponseEntity<List<StudentDTO>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<StudentDTO>>(students, HttpStatus.OK);
	}

	// find one student
	@RequestMapping(value = "/student/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentDTO> getStudent(@PathVariable("id") long id) {

		String msg = "Finding student with id " + id;
		logger.info(msg);

		StudentDTO student = studentService.findStudentById(id);

		if (student == null) {
			// logger.info("Student with id " + id + " not found.");
			msg = "Student with id " + id + " not found.";
			logger.warn(msg);
			return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<StudentDTO>(student, HttpStatus.OK);
	}

	// find one student address
	@RequestMapping(value = "/address/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddressDTO> getAddress(@PathVariable("id") long id) {

		logger.info("Finding student address with id " + id);

		AddressDTO address = studentService.getAddressById(id);

		if (address == null) {
			logger.warn("Student with address id " + id + " not found.");
			return new ResponseEntity<AddressDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<AddressDTO>(address, HttpStatus.OK);
	}

	/*
	 * // Create a student
	 * 
	 * @RequestMapping(value = "/student", method = RequestMethod.POST) public
	 * ResponseEntity<Void> createStudent(@RequestBody StudentDTO student,
	 * UriComponentsBuilder ucBuilder ) { logger.info("Creating Student " +
	 * student.getName());
	 * 
	 * if (studentService.isStudentExist(student)) {
	 * logger.info("A User with name " + student.getName() + " already exist");
	 * return new ResponseEntity<Void>(HttpStatus.CONFLICT); }
	 * 
	 * studentService.saveStudent(student);
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setLocation(ucBuilder.path("/student/{id}").buildAndExpand(
	 * student.getId()).toUri()); return new ResponseEntity<Void>(headers,
	 * HttpStatus.CREATED); }
	 */

	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public ResponseEntity<Void> createStudent(@RequestBody StudentDTO student, HttpSession session) {

		logger.info("createStudent entered. Studentdto object " + student );
		logger.info("createStudent saving into session object " + session );
		// do some validation and save object to session variable.
		session.setAttribute("studentdto", student);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// Create an address object and do inserts to database.
	@RequestMapping(value = "/address", method = RequestMethod.POST)
	public ResponseEntity<StudentDTO> createAddress(@RequestBody AddressDTO address, HttpSession session) {

		logger.info("createAddress entered with " + address );
		logger.info("createAddress using session object " + session );
		// DO SOME VALIDATION on address here ???
		// retrieve student object from session
		StudentDTO student = (StudentDTO) session.getAttribute("studentdto");

		if (student == null) {
			logger.warn("studentdto object not found in session.");
			return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
		}

		// get the next sequence value.
		long id = studentService.getNextStudentId();
		// use transaction to insert into both tables
		studentService.saveStudent(id, student, address);
		// if everything is good ... then we can fetch this id. And also place
		// the id into the cache.
		student = studentService.findStudentById(id);
		return new ResponseEntity<StudentDTO>(student, HttpStatus.OK);
	}

	// Update a student
	@RequestMapping(value = "/student/{id}", method = RequestMethod.PUT)
	public ResponseEntity<StudentDTO> updateStudent(@PathVariable("id") long id, @RequestBody StudentDTO student) {

		logger.info("updateStudent with student " + id);

		StudentDTO currentStd = studentService.findStudentById(id);

		if (currentStd == null) {
			logger.warn("Student with id " + id + " not found.");
			return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
		}

		studentService.updateStudent(id, student);
		// get the updated student object and return it.
		currentStd = studentService.findStudentById(id);
		return new ResponseEntity<StudentDTO>(currentStd, HttpStatus.OK);
	}

	// Update an address
	@RequestMapping(value = "/address/{id}", method = RequestMethod.PUT)
	public ResponseEntity<AddressDTO> updateAddress(@PathVariable("id") long id, @RequestBody AddressDTO address) {

		logger.info("updateAddress entered with address " + id);

		StudentDTO student = studentService.findStudentById(id);

		if (student == null) {
			logger.warn("Student with id " + id + " not found");
			return new ResponseEntity<AddressDTO>(HttpStatus.NOT_FOUND);
		}

		studentService.updateAddress(id, address);
		AddressDTO curraddress = studentService.getAddressById(id);
		return new ResponseEntity<AddressDTO>(curraddress, HttpStatus.OK);
	}

	// Delete a student
	@RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<StudentDTO> deleteStudent(@PathVariable("id") long id) {

		logger.info("Fetching & Deleting Student with id " + id);

		StudentDTO user = studentService.findStudentById(id);
		if (user == null) {
			logger.warn("Unable to delete. Student with id " + id + " not found");
			return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
		}

		studentService.deleteStudent(id);
		return new ResponseEntity<StudentDTO>(HttpStatus.NO_CONTENT);
	}

}
