package com.klef.sdp.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="subject_table")
public class Subject {

    @Id
    @Column(length=20, nullable=false)
    private String coursecode;

    @Column(length=40, nullable=false, unique=true)
    private String subjectname;

    @Column(length=15, nullable=false)
    private String department;

    @Column(nullable=false)
    private int semester;

    @Column(nullable=false)
    private double credits;

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public double getCredits() {
		return credits;
	}

	public void setCredits(double credits) {
		this.credits = credits;
	}

	@Override
	public String toString() {
		return "Subject [coursecode=" + coursecode + ", subjectname=" + subjectname + ", department=" + department
				+ ", semester=" + semester + ", credits=" + credits + "]";
	}
}