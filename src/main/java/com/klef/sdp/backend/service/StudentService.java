package com.klef.sdp.backend.service;

import java.util.List;

import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.entity.Submission;

public interface StudentService {
	public Student verifyStudentLogin(String email, String password);
	
	public String updateStudentProfile(Student student);
	
    public List<Subject> viewSubjectsByDepartment(String department);
	
    public List<Project> viewProjectsBySubject(String coursecode);
    
    public List<ProjectGroup> viewGroupsByProject(int projectId);
    public String joinGroup(int groupId, int studentId);
	public List<Student> viewMembersByGroup(int groupId);
	
	// Submission methods
	public String submitProject(Submission submission, int groupId, int studentId);
	public List<Submission> viewSubmissionsByGroup(int groupId);
	public boolean isGroupLeader(int studentId, int groupId);
	
	// PDF Upload methods
	public String uploadPDF(org.springframework.web.multipart.MultipartFile file, int groupId, int studentId, String description);
	public Submission getSubmissionById(int submissionId);
	
	// Get all groups for a student
	public List<ProjectGroup> getAllGroupsByStudent(int studentId);
}
