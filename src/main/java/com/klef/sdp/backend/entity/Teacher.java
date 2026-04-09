 package com.klef.sdp.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Teacher_table")
public class Teacher {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

@Column(length=100,nullable = false)
private String name;

@Column(length=50,nullable = false)
private String department;

@Column(length=50,nullable = false)
private String username;

@Column(length=50,nullable = false)
private String password;

@Column(length=100,nullable = false,unique = true)
private String email;

@Column(length=50,nullable = false,unique = true)
private String contact;

@Column(length=50,nullable = false)
private String designation;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getDepartment() {
	return department;
}

public void setDepartment(String department) {
	this.department = department;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getContact() {
	return contact;
}

public void setContact(String contact) {
	this.contact = contact;
}

public String getDesignation() {
	return designation;
}

public void setDesignation(String designation) {
	this.designation = designation;
}

@Override
public String toString() {
	return "Teacher [id=" + id + ", name=" + name + ", department=" + department + ", username=" + username
			+ ", password=" + password + ", email=" + email + ", contact=" + contact + ", designation=" + designation
			+ "]";
}

}
