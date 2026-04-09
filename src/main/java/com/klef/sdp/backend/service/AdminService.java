package com.klef.sdp.backend.service;


import java.util.List;

import com.klef.sdp.backend.entity.Admin;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.entity.Teacher;

public interface AdminService {

	 public Admin verifyAdminLogin(String username,String password);
	 
	 public String addTeacher(Teacher t);
	 public String addStudent(Student s);
	 
	 public List<Student> viewAllStudents();
	 public List<Teacher> viewAllTeachers();
	 
	 public  boolean deleteStudent(int id);
	 public  boolean deleteTeacher(int id);
	 
	 public long totalStudents();
	 public long totalTeachers();
	 
	 public String addSubject(Subject subject);
	 public List<Subject> viewAllSubjects();
	 public boolean deleteSubject(String coursecode);
}
