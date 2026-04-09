package com.klef.sdp.backend.service;

import java.util.List;

import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;

public interface StudentService {
	public Student verifyStudentLogin(String email, String password);
	
	public String updateStudentProfile(Student student);
	
    public List<Subject> viewSubjectsByDepartment(String department);
	
    public List<Project> viewProjectsBySubject(String coursecode);
    
    public List<ProjectGroup> viewGroupsByProject(int projectId);
    public String joinGroup(int groupId, int studentId);
	public List<Student> viewMembersByGroup(int groupId);
}
