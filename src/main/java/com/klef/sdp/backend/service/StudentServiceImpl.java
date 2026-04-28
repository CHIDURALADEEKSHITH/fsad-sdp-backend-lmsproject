package com.klef.sdp.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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
import com.klef.sdp.backend.repository.ProjectGroupRepository;
import com.klef.sdp.backend.repository.ProjectRepository;
import com.klef.sdp.backend.repository.StudentRepository;
import com.klef.sdp.backend.repository.SubjectRepository;
import com.klef.sdp.backend.repository.SubmissionRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectGroupRepository projectGroupRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Override
    public Student verifyStudentLogin(String email, String password) {
        return studentRepository.checkLogin(email, password);
    }

    @Override
    public String updateStudentProfile(Student student) {
        Optional<Student> optional = studentRepository.findById(student.getId());

        if (optional.isPresent()) {
            Student s = optional.get();
            s.setContact(student.getContact());
            s.setBloodgroup(student.getBloodgroup());
            s.setPassword(student.getPassword());

            studentRepository.save(s);
            return "Profile Updated Successfully";
        } else {
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

        if (subject == null) {
            return new ArrayList<>();
        }

        return projectRepository.findBySubject(subject);
    }

    @Override
    public List<ProjectGroup> viewGroupsByProject(int projectId) {
        return projectGroupRepository.findByProjectId(projectId);
    }

    @Override
    public String joinGroup(int groupId, int studentId) {
        try {
            Optional<ProjectGroup> optional = projectGroupRepository.findById(groupId);

            if (optional.isEmpty()) {
                return "Group Not Found";
            }

            ProjectGroup group = optional.get();

            if (group.getProject() == null) {
                return "Project not found for this group";
            }

            int projectId = group.getProject().getId();

            if (projectGroupRepository.checkStudentInProject(studentId, projectId) > 0) {
                return "You Already Joined a Group for this Project";
            }

            if (group.getMembers() == null) {
                group.setMembers(new HashSet<>());
            }

            if (group.getMembers().size() >= group.getMaxMembers()) {
                return "Group is Full";
            }

            Optional<Student> studentOpt = studentRepository.findById(studentId);

            if (studentOpt.isEmpty()) {
                return "Student Not Found";
            }

            Student student = studentOpt.get();

            group.getMembers().add(student);
            projectGroupRepository.save(group);

            return "Joined Group Successfully";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error joining group: " + e.getMessage();
        }
    }

    @Override
    public List<Student> viewMembersByGroup(int groupId) {
        ProjectGroup group = projectGroupRepository.findById(groupId).orElse(null);

        if (group == null || group.getMembers() == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(group.getMembers());
    }

    @Override
    public List<ProjectGroup> getAllGroupsByStudent(int studentId) {
        return projectGroupRepository.findByStudentId(studentId);
    }

    @Override
    public String submitProject(Submission submission, int groupId, int studentId) {
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

        if (group.getLeader() == null || group.getLeader().getId() != studentId) {
            return "Only Group Leader Can Submit Project";
        }

        submission.setProjectGroup(group);
        submission.setSubmittedBy(student);
        submission.setSubmittedAt(LocalDateTime.now());

        submissionRepository.save(submission);

        return "Project Submitted Successfully";
    }

    @Override
    public String uploadPDF(MultipartFile file, int groupId, int studentId, String description) {
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

            if (group.getLeader() == null || group.getLeader().getId() != studentId) {
                return "Only Group Leader Can Submit Project";
            }

            String uploadDir = "uploads/submissions/" + groupId;
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            Submission submission = new Submission();
            submission.setFileName(originalFilename);
            submission.setFilePath(filePath.toString());
            submission.setContentType(file.getContentType());
            submission.setFileSize(file.getSize());
            submission.setDescription(description);
            submission.setSubmittedAt(LocalDateTime.now());
            submission.setProjectGroup(group);
            submission.setSubmittedBy(student);

            submissionRepository.save(submission);

            return "PDF Submitted Successfully";

        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading file: " + e.getMessage();
        }
    }

    @Override
    public Submission getSubmissionById(int submissionId) {
        Optional<Submission> subOpt = submissionRepository.findById(submissionId);
        return subOpt.orElse(null);
    }

    @Override
    public List<Submission> viewSubmissionsByGroup(int groupId) {
        return submissionRepository.findByProjectGroupId(groupId);
    }

    @Override
    public boolean isGroupLeader(int studentId, int groupId) {
        Optional<ProjectGroup> groupOpt = projectGroupRepository.findById(groupId);

        if (groupOpt.isEmpty()) {
            return false;
        }

        ProjectGroup group = groupOpt.get();

        return group.getLeader() != null && group.getLeader().getId() == studentId;
    }
}