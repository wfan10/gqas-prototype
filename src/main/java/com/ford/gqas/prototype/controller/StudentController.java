package com.ford.gqas.prototype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.*;

import com.ford.gqas.prototype.service.StudentService;
import com.ford.gqas.prototype.model.*;

// This way works to allow query from a different origin. 
@CrossOrigin( origins = "http://localhost:8081" )
@Controller
public class StudentController {

	@Autowired
	StudentService studentService;

	// get all students
	@RequestMapping( value = "/student/", method = RequestMethod.GET )
	public ResponseEntity<List<StudentDTO>> listAllStudents() {

		List<StudentDTO> students = studentService.findAllStudents();

		if (students.isEmpty()) {
			// You many decide to return HttpStatus.NOT_FOUND
			return new ResponseEntity<List<StudentDTO>>( HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<StudentDTO>>(students, HttpStatus.OK);
	}

	// find one student
	@RequestMapping( value = "/student/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<StudentDTO> getStudent( @PathVariable("id") long id ){
		System.out.println("Finding studenbt with id " + id );
		
		StudentDTO student = studentService.findById(id);
		
		if ( student == null ){
			System.out.println("Student with id " + id + " not found.");
			return new ResponseEntity<StudentDTO>( HttpStatus.NOT_FOUND );
		}
		
		return new ResponseEntity<StudentDTO>( student, HttpStatus.OK);
	}
	
	// Create a student
    @RequestMapping(value = "/student/", method = RequestMethod.POST)
    public ResponseEntity<Void> createStudent(@RequestBody StudentDTO student, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Student " + student.getName());
 
        if ( studentService.isStudentExist(student) ) {
            System.out.println("A User with name " + student.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        studentService.saveStudent(student);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/student/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
	
	// Update a student
    @RequestMapping(value = "/student/{id}", method = RequestMethod.PUT)
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable("id") long id, @RequestBody StudentDTO student) {
        System.out.println("Updating Student " + id);
         
        StudentDTO currentStd = studentService.findById(id);
         
        if (currentStd == null) {
            System.out.println("Student with id " + id + " not found");
            return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
        }
 
        currentStd.setName( student.getName());
        currentStd.setAge( student.getAge());
         
        studentService.updateStudent(currentStd);
        return new ResponseEntity<StudentDTO>(currentStd, HttpStatus.OK);
    }
	
	// Delete a student
    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<StudentDTO> deleteStudent( @PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Student with id " + id);
 
        StudentDTO user = studentService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. Student with id " + id + " not found");
            return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
        }
 
        studentService.deleteStudent(id);
        return new ResponseEntity<StudentDTO>(HttpStatus.NO_CONTENT);
    }
}
