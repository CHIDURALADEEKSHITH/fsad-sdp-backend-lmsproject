package com.klef.sdp.backend.service;

import java.util.List;

import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.entity.Teacher;

public interface TeacherService {

	   public Teacher verifyTeacherLogin(String email, String password);

	    public String updateTeacherProfile(Teacher teacher);
	    
	    public List<Subject> viewSubjectsByDepartment(String department);
	    
	    public String addProject(Project project, String coursecode, int teacherId);
	    public List<Project> viewProjectsBySubject(String coursecode);
	    public boolean deleteProject(int projectId);

	    
	    public String createGroup(int projectId, int maxMembers); 
	    public String assignLeader(int groupId, int studentId); 
	    public List<ProjectGroup> viewGroupsByProject(int projectId);
	    public boolean deleteGroup(int groupId);
	    public List<Student> viewMembersByGroup(int groupId);//
}
