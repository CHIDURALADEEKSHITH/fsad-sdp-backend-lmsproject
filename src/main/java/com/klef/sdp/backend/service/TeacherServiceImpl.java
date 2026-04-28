package com.klef.sdp.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.entity.Submission;
import com.klef.sdp.backend.entity.Teacher;
import com.klef.sdp.backend.repository.ProjectGroupRepository;
import com.klef.sdp.backend.repository.ProjectRepository;
import com.klef.sdp.backend.repository.StudentRepository;
import com.klef.sdp.backend.repository.SubjectRepository;
import com.klef.sdp.backend.repository.TeacherRepository;
import com.klef.sdp.backend.repository.SubmissionRepository;

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

@Autowired
private SubmissionRepository submissionRepository;



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
	        if (optional.isPresent()) {
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
	        try {
	            Optional<ProjectGroup> groupOpt = projectGroupRepository.findById(groupId);
	            if (groupOpt.isEmpty()) {
	                return "Group Not Found";
	            }

	            Optional<Student> studentOpt = studentRepository.findById(studentId);
	            if (studentOpt.isEmpty()) {
	                return "Student Not Found";
	            }

	            ProjectGroup group = groupOpt.get();
	            Student student = studentOpt.get();

	            // 🔥 IMPORTANT FIX
	            if (group.getMembers() == null) {
	                group.setMembers(new java.util.HashSet<>());
	            }

	            group.getMembers().add(student);   
	            group.setLeader(student);         

	            projectGroupRepository.save(group);

	            return "Leader Assigned Successfully";

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Error assigning leader: " + e.getMessage();
	        }
	    }
	    
    @Override
    public String evaluateSubmission(int submissionId, int marks, String feedback, int teacherId) {
	        Optional<Submission> subOpt = submissionRepository.findById(submissionId);
	        if (subOpt.isEmpty()) {
	            return "Submission Not Found";
	        }
	        
	        Optional<Teacher> teacherOpt = teacherRepository.findById(teacherId);
	        if (teacherOpt.isEmpty()) {
	            return "Teacher Not Found";
	        }
	        
	        Submission submission = subOpt.get();
	        submission.setMarks(marks);
	        submission.setFeedback(feedback);
	        submission.setEvaluatedBy(teacherOpt.get());
	        submission.setEvaluatedAt(LocalDateTime.now());
	        
	        submissionRepository.save(submission);
	        return "Submission Evaluated Successfully";
	    }
	
	@Override
	public Submission getSubmissionById(int submissionId) {
		Optional<Submission> subOpt = submissionRepository.findById(submissionId);
		return subOpt.orElse(null);
	}

	@Override
	public List<Student> viewMembersByGroup(int groupId) {

	    ProjectGroup group = projectGroupRepository.findById(groupId).orElse(null);

	    if (group == null || group.getMembers() == null) {
	        return new java.util.ArrayList<>();
	    }

	    return new java.util.ArrayList<>(group.getMembers());
	}
	
	@Override
	public List<Submission> viewSubmissionsByProject(int projectId) {

	    Project project = projectRepository.findById(projectId).orElse(null);

	    if (project == null) {
	        return new java.util.ArrayList<>();
	    }

	    List<ProjectGroup> groups = projectGroupRepository.findByProject(project);

	    List<Submission> allSubmissions = new java.util.ArrayList<>();

	    for (ProjectGroup group : groups) {
	        List<Submission> subs = submissionRepository.findByProjectGroupId(group.getId());
	        allSubmissions.addAll(subs);
	    }

	    return allSubmissions;
	}


@Override
public String addProjectWithFile(Project project, String coursecode, int teacherId, MultipartFile file) {
    try {
        Subject subject = subjectRepository.findById(coursecode).orElse(null);
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);

        if (subject == null) {
            return "Subject Not Found";
        }

        if (teacher == null) {
            return "Teacher Not Found";
        }

        project.setSubject(subject);
        project.setTeacher(teacher);

        if (file != null && !file.isEmpty()) {
            if (!"application/pdf".equals(file.getContentType())) {
                return "Only PDF files are allowed";
            }

            String uploadDir = "uploads/projectfiles";
            java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);

            if (!java.nio.file.Files.exists(uploadPath)) {
                java.nio.file.Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            java.nio.file.Path filePath = uploadPath.resolve(fileName);

            java.nio.file.Files.copy(
                file.getInputStream(),
                filePath,
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            project.setFileName(file.getOriginalFilename());
            project.setFilePath(filePath.toString());
        }

        projectRepository.save(project);
        return "Project Added Successfully";

    } catch (Exception e) {
        e.printStackTrace();
        return "Error adding project: " + e.getMessage();
    }
}

@Override
public Project getProjectById(int projectId) {
    return projectRepository.findById(projectId).orElse(null);
}


@Override
public byte[] downloadProjectFile(int projectId) {

    Project project = projectRepository.findById(projectId).orElse(null);

    if (project == null) {
        return null;
    }

    if (project.getFilePath() == null) {
        return null;
    }

    try {
        java.io.File file = new java.io.File(project.getFilePath());

        if (!file.exists()) {
            return null;
        }

        return java.nio.file.Files.readAllBytes(file.toPath());

    } catch (Exception e) {
        return null;
    }
}
}
