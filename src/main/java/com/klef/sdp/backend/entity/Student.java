package com.klef.sdp.backend.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="Student_table")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
	
	@Column(length=100,nullable = false)
private String name;//

	@Column(length=10,nullable = false)
private String gender;

	@Column(length=100,nullable = false,unique = true)
private String email;

	@Column(length=20,nullable = false,unique = true)
private String contact;

	@Column(length=50,nullable = false)
private String username;

	@Column(length=50,nullable = false)
private String password;

	@Column(length=20,nullable = false)
private String department;

	@Column(length=20,nullable = false)
private String bloodgroup;

@CreatedBy
@Column(updatable=false) 
private LocalDateTime registeredAt;


@JsonIgnore
@JsonIgnoreProperties({"members", "project", "leader"})
@ManyToOne
@JoinColumn(name="group_id")
private ProjectGroup group;

@JsonIgnore
@JsonIgnoreProperties({"members", "project", "leader"})
@ManyToMany(mappedBy = "members")
private Set<ProjectGroup> groups = new HashSet<>();

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

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
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

public String getDepartment() {
	return department;
}

public void setDepartment(String department) {
	this.department = department;
}

public String getBloodgroup() {
	return bloodgroup;
}

public void setBloodgroup(String bloodgroup) {
	this.bloodgroup = bloodgroup;
}

public LocalDateTime getRegisteredAt() {
	return registeredAt;
}

public void setRegisteredAt(LocalDateTime registeredAt) {
	this.registeredAt = registeredAt;
}

public ProjectGroup getGroup() {
	return group;
}

public void setGroup(ProjectGroup group) {
	this.group = group;
}

public Set<ProjectGroup> getGroups() {
	return groups;
}

public void setGroups(Set<ProjectGroup> groups) {
	this.groups = groups;
}

public void addGroup(ProjectGroup projectGroup) {
    this.groups.add(projectGroup);
    projectGroup.getMembers().add(this);
}

public void removeGroup(ProjectGroup projectGroup) {
    this.groups.remove(projectGroup);
    projectGroup.getMembers().remove(this);
}

@Override
public String toString() {
	return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", email=" + email + ", contact=" + contact
			+ ", username=" + username + ", password=" + password + ", department=" + department + ", bloodgroup="
			+ bloodgroup + ", registeredAt=" + registeredAt + "]";
}

}
