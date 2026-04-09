package com.klef.sdp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.repository.ProjectGroupRepository;
import com.klef.sdp.backend.repository.ProjectRepository;
import com.klef.sdp.backend.repository.StudentRepository;
import com.klef.sdp.backend.repository.SubjectRepository;

@Service
public class StudentServiceImpl implements StudentService {

@Autowired
private StudentRepository studentRepository;

@Autowired
private SubjectRepository subjectRepository;

@Autowired
private ProjectRepository projectRepository;

@Autowired
private ProjectGroupRepository  projectGroupRepository;

	@Override
	public Student verifyStudentLogin(String email, String password) {
		
        return studentRepository.checkLogin(email, password);
	}

	@Override
	public String updateStudentProfile(Student student) {
	
		Optional<Student> optional = studentRepository.findById(student.getId());
		if(optional.isPresent()) {
			Student s=optional.get();
			s.setContact(student.getContact());
			s.setBloodgroup(student.getBloodgroup());
			s.setPassword(student.getPassword());
			
			return "Profile Updated Successfully";
		}else {
			return "ID Not Found to Update";
		}
		
	}

	@Override
	public List<Subject> viewSubjectsByDepartment(String department) {
		return subjectRepository.findByDepartment(department);
	}

	@Override
	public List<Project> viewProjectsBySubject(String coursecode) {
	    Subject subject = subjectRepository.findById(coursecode).orElse(null);
	    return projectRepository.findBySubject(subject);
	}

    @Override
	public List<ProjectGroup> viewGroupsByProject(int projectId) {
	    return projectGroupRepository.findByProjectId(projectId);
	}

    @Override
    public String joinGroup(int groupId, int studentId) {
        if(studentRepository.checkStudentInGroup(studentId) > 0)
            return "You Already Joined a Group";

        Optional<ProjectGroup> optional = projectGroupRepository.findById(groupId);
        if(optional.isPresent()) {
            if(projectGroupRepository.countMembersByGroup(optional.get()) >= optional.get().getMaxMembers())
                return "Group is Full";
            studentRepository.joinGroup(studentId, groupId); // pass groupId not group object
            return "Joined Group Successfully";
        }
        return "Group Not Found";
    }

	@Override
	public List<Student> viewMembersByGroup(int groupId) {
		return studentRepository.getStudentsByGroupId(groupId);
	}
}
