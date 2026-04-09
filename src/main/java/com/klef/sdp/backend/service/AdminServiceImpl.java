package com.klef.sdp.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.backend.entity.Admin;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.entity.Teacher;
import com.klef.sdp.backend.repository.AdminRepository;
import com.klef.sdp.backend.repository.StudentRepository;
import com.klef.sdp.backend.repository.SubjectRepository;
import com.klef.sdp.backend.repository.TeacherRepository;
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentRepository studentRepository; 
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Override
	public Admin verifyAdminLogin(String username, String password) {
		
		return adminRepository.findByUsernameAndPassword(username, password);
	}

	@Override
	public String addTeacher(Teacher t) {
		if(teacherRepository.existsByEmail(t.getEmail())) {
		    return "Teacher Email Already Exists";
		}
		teacherRepository.save(t);
		return "Teacher Added Successfully";
	}

	@Override
	public String addStudent(Student s) {
		if(studentRepository.existsByEmail(s.getEmail())) {
		    return "Student Email Already Exists";
		}
		studentRepository.save(s);
		return "Student Added Successfully";
		
	}

	@Override
	public List<Student> viewAllStudents() {
	return studentRepository.findAll();
		
	}

	@Override
	public List<Teacher> viewAllTeachers() {
	
		return  teacherRepository.findAll();
	}

	@Override
	public boolean deleteStudent(int id) {
		if(studentRepository.existsById(id)) {
			studentRepository.deleteById(id);
			return true;
		}else {
			return false;
		}
	}

	@Override 
	public boolean deleteTeacher(int id) {
		if(teacherRepository.existsById(id)) {
			teacherRepository.deleteById(id);
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public long totalStudents() {
		return studentRepository.totalStudents();
	}

	@Override 
	public long totalTeachers() {
		return teacherRepository.totalTeachers();
	}

	@Override
	public String addSubject(Subject subject) {
		if(subjectRepository.existsById(subject.getCoursecode())) {
			return "CourseCode Already Exists";
		}
		subjectRepository.save(subject);
		return "Subject Added Successfully";
	}

	@Override
	public List<Subject> viewAllSubjects() {
		
		return subjectRepository.findAll();
	}

	@Override
	public boolean deleteSubject(String coursecode) {
		if(subjectRepository.existsById(coursecode)) {
			subjectRepository.deleteById(coursecode);
			return true;
		}
		return false;
	}

}
