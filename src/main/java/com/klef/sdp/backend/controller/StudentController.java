package com.klef.sdp.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
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
	
	@PostMapping("login")
	public ResponseEntity<?> verifyStudentLogin(@RequestBody Student student){
		 try {
			  Student s = studentService.verifyStudentLogin(student.getEmail(), student.getPassword());
			  if(s!=null) {
				  return ResponseEntity.status(200).body(s);
			  }else {
				  return ResponseEntity.status(401).body("Invalid login");
			  }
		 }catch(Exception e) {
			 return ResponseEntity.status(500).body("Internal Server Error");
		 }
	}
	
	
	@PostMapping("/updateprofile")
	public ResponseEntity<?> updateStudentProfile(@RequestBody Student student){
	    try {
	        String output = studentService.updateStudentProfile(student);
	        
	            return ResponseEntity.status(200).body(output);
	    } catch(Exception e) {
	        return ResponseEntity.status(500).body("Internal Server Error");
	    }
	}
	
	@GetMapping("viewsubjectbydepartment")
	public ResponseEntity<?> viewSubjectByDepartment(@RequestParam String department){
		try {
			List<Subject> subjects =studentService.viewSubjectsByDepartment(department);
			return ResponseEntity.ok(subjects);
		}catch(Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}
	
    @GetMapping("/viewprojectsbysubject")
    public ResponseEntity<?> viewProjectsBySubject(@RequestParam String coursecode) {
        try {
            List<Project> projects = studentService.viewProjectsBySubject(coursecode);
            return ResponseEntity.ok(projects);
        } catch(Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
    
    @GetMapping("/viewgroupsbyproject")
    public ResponseEntity<?> viewGroupsByProject(@RequestParam int projectId) {
        try {
            List<ProjectGroup> groups = studentService.viewGroupsByProject(projectId);
            return ResponseEntity.ok(groups);
        } catch(Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
    
    @PostMapping("/joingroup")
    public ResponseEntity<String> joinGroup(@RequestParam int groupId, @RequestParam int studentId) {
        try {
            String output = studentService.joinGroup(groupId, studentId);
            return ResponseEntity.status(200).body(output);
        } catch(Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
    
    @GetMapping("/viewmembersbygroup")
    public ResponseEntity<?> viewMembersByGroup(@RequestParam int groupId) {
        try {
            List<Student> members = studentService.viewMembersByGroup(groupId);
            return ResponseEntity.ok(members);
        } catch(Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
	
}
