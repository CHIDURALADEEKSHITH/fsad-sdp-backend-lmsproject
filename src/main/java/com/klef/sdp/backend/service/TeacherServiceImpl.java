package com.klef.sdp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.entity.Teacher;
import com.klef.sdp.backend.repository.ProjectGroupRepository;
import com.klef.sdp.backend.repository.ProjectRepository;
import com.klef.sdp.backend.repository.StudentRepository;
import com.klef.sdp.backend.repository.SubjectRepository;
import com.klef.sdp.backend.repository.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {

@Autowired
private TeacherRepository teacherRepository;

@Autowired
private SubjectRepository subjectRepository;

@Autowired
private ProjectRepository projectRepository;

@Autowired
private ProjectGroupRepository projectGroupRepository;

@Autowired
private StudentRepository studentRepository;



	@Override
	public Teacher verifyTeacherLogin(String email, String password) {
		return teacherRepository.checkLogin(email, password);
		
	}

	@Override
	public String updateTeacherProfile(Teacher teacher) {
		Optional<Teacher> optional = teacherRepository.findById(teacher.getId());
		if(optional.isPresent()) {
			 Teacher t=optional.get();
			 t.setContact(teacher.getContact());
			 t.setDesignation(teacher.getDesignation());
			 t.setPassword(teacher.getPassword());
			 t.setDepartment(teacher.getDepartment());
			 teacherRepository.save(t);
			 return "Teacher Profile Upadted Successfully";
		}else {
			return "ID Not Found To Update";
		}
	}

	@Override
	public List<Subject> viewSubjectsByDepartment(String department) {
		return subjectRepository.findByDepartment(department);
	}

	 @Override
	    public String addProject(Project project, String coursecode, int teacherId) {
	        Subject subject = subjectRepository.findById(coursecode).orElse(null);
	        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
	        project.setSubject(subject);
	        project.setTeacher(teacher);
	        projectRepository.save(project);
	        return "Project Added Successfully";
	    }

	    @Override
	    public List<Project> viewProjectsBySubject(String coursecode) { // can view projects
	        Subject subject = subjectRepository.findById(coursecode).orElse(null);
	        return projectRepository.findBySubject(subject);
	    }

	    @Override
	    public boolean deleteProject(int projectId) {
	        if(projectRepository.existsById(projectId)) {
	            projectRepository.deleteById(projectId);
	            return true;
	        }
	        return false;
	    }

	    @Override
	    public String createGroup(int projectId, int maxMembers) {
	        Optional<Project> optional = projectRepository.findById(projectId);
	        if(optional.isPresent()) {
	            ProjectGroup group = new ProjectGroup();
	            group.setProject(optional.get());
	            group.setMaxMembers(maxMembers);
	            group.setGroupName("Group");
	            projectGroupRepository.save(group);
	            return "Group Created Successfully";
	        }
	        return "Project Not Found";
	    }

	      @Override
	      public List<ProjectGroup> viewGroupsByProject(int projectId) {
	        Project project = projectRepository.findById(projectId).orElse(null);
	        return projectGroupRepository.findByProject(project);
	    }

	    @Override
	    public boolean deleteGroup(int groupId) {
	        if(projectGroupRepository.existsById(groupId)) { //
	            projectGroupRepository.deleteById(groupId);
	            return true;
	          }
	            return false;
	    }

	    @Override
	    public String assignLeader(int groupId, int studentId) {
	        Optional<Student> optional = studentRepository.findById(studentId);
	        if(optional.isPresent()) {
	            projectGroupRepository.assignLeader(groupId, optional.get());
	            return "Leader Assigned Successfully";
	        } else {
	            return "Student Not Found";
	        }
	    }
	    
	    @Override
	    public List<Student> viewMembersByGroup(int groupId) {
	        return studentRepository.getStudentsByGroupId(groupId);
	    }
	
}
