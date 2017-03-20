package com.ford.gqas.prototype.model;


// Note that we do not have to use prototype annotation for this class.
// Reason is we create a new instance in the *DAO class for each SQL query.  
// @Scope("prototype") 
public class StudentDTO {

	private long id;
	private String name;
	private int age;

	public StudentDTO(){
	}

	public StudentDTO( long id, String name, int age ){
		this.id = id;
		this.name = name;
		this.age = age;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
}
