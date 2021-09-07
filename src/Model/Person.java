package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.Gender;
import application.Main;

public abstract class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String firstName;
	private String lastName;
	private LocalDate birthDay;
	private Gender gender;
	
	// New for GUI
	private String userName;

	private String password;
	
	
	public Person(int id, String firstName, String lastName, LocalDate birthDay, Gender gender, String userName, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
		this.gender = gender;
		
		this.userName = userName;
		this.password = password;

		if (Main.getRest().getUserNames() == null)
			Main.getRest().resetUserNames();
		Main.getRest().getUserNames().add(userName); // add User name for Set of all userNames

	}
	
	public Person(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	// New For GUI
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
	
//	
	
}
