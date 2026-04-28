package com.klef.sdp.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.entity.Submission;
import com.klef.sdp.backend.service.StudentService;

@RestController
@RequestMapping("/studentapi")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String studenthome() {
        return "Student Demo";
    }

    @PostMapping("/login")
    public ResponseEntity<?> verifyStudentLogin(@RequestBody Student student) {
        try {
            Student s = studentService.verifyStudentLogin(student.getEmail(), student.getPassword());
            if (s != null) {
                return ResponseEntity.ok(s);
            } else {
                return ResponseEntity.status(401).body("Invalid login");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/updateprofile")
    public ResponseEntity<?> updateStudentProfile(@RequestBody Student student) {
        try {
            String output = studentService.updateStudentProfile(student);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/viewsubjectbydepartment")
    public ResponseEntity<?> viewSubjectByDepartment(@RequestParam String department) {
        try {
            List<Subject> subjects = studentService.viewSubjectsByDepartment(department);
            return ResponseEntity.ok(subjects);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/viewprojectsbysubject")
    public ResponseEntity<?> viewProjectsBySubject(@RequestParam String coursecode) {
        try {
            List<Project> projects = studentService.viewProjectsBySubject(coursecode);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/viewgroupsbyproject")
    public ResponseEntity<?> viewGroupsByProject(@RequestParam int projectId) {
        try {
            List<ProjectGroup> groups = studentService.viewGroupsByProject(projectId);
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/joingroup")
    public ResponseEntity<String> joinGroup(@RequestParam int groupId, @RequestParam int studentId) {
        try {
            String output = studentService.joinGroup(groupId, studentId);

            if (output.contains("Successfully")) {
                return ResponseEntity.ok(output);
            } else if (output.contains("Error")) {
                return ResponseEntity.status(500).body(output);
            } else {
                return ResponseEntity.status(400).body(output);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/viewmembersbygroup")
    public ResponseEntity<?> viewMembersByGroup(@RequestParam int groupId) {
        try {
            List<Student> members = studentService.viewMembersByGroup(groupId);
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/mygroups")
    public ResponseEntity<?> getMyGroups(@RequestParam int studentId) {
        try {
            List<ProjectGroup> groups = studentService.getAllGroupsByStudent(studentId);
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/submitproject")
    public ResponseEntity<?> submitProject(@RequestBody Submission submission,
                                           @RequestParam int groupId,
                                           @RequestParam int studentId) {
        try {
            String output = studentService.submitProject(submission, groupId, studentId);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/uploadpdf")
    public ResponseEntity<?> uploadPDF(@RequestParam("file") MultipartFile file,
                                       @RequestParam int groupId,
                                       @RequestParam int studentId,
                                       @RequestParam(required = false) String description) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(400).body("Please select a file");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.equals("application/pdf")) {
                return ResponseEntity.status(400).body("Only PDF files are allowed");
            }

            String output = studentService.uploadPDF(file, groupId, studentId, description);
            return ResponseEntity.ok(output);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/downloadsubmission")
    public ResponseEntity<?> downloadSubmission(@RequestParam int submissionId) {
        try {
            Submission submission = studentService.getSubmissionById(submissionId);

            if (submission == null) {
                return ResponseEntity.status(404).body("Submission not found");
            }

            java.io.File file = new java.io.File(submission.getFilePath());

            if (!file.exists()) {
                return ResponseEntity.status(404).body("File not found on server");
            }

            byte[] fileData = java.nio.file.Files.readAllBytes(file.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", submission.getFileName());
            headers.setContentLength(fileData.length);

            return ResponseEntity.ok().headers(headers).body(fileData);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/viewsubmissionsbygroup")
    public ResponseEntity<?> viewSubmissionsByGroup(@RequestParam int groupId) {
        try {
            List<Submission> submissions = studentService.viewSubmissionsByGroup(groupId);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/isgroupleader")
    public ResponseEntity<?> isGroupLeader(@RequestParam int studentId, @RequestParam int groupId) {
        try {
            boolean isLeader = studentService.isGroupLeader(studentId, groupId);
            return ResponseEntity.ok(isLeader);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }
}