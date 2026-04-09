package com.klef.sdp.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.sdp.backend.entity.Admin;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.entity.Teacher;
import com.klef.sdp.backend.service.AdminService;

@RestController
@RequestMapping("adminapi")
@CrossOrigin("*")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/")
	public String index() {
		return "SDP Project";
	}
	 
	@PostMapping("/login")
	 public ResponseEntity<?> checkAdminLogin(@RequestBody Admin admin) {
        try {
            Admin a = adminService.verifyAdminLogin(admin.getUsername(), admin.getPassword());
            if(a != null) {
                return ResponseEntity.status(200).body(a);
            } else {
                return ResponseEntity.status(401).body("Invalid Credentials");
            }
        } catch(Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
	
	@PostMapping("/addstudent")
	  public ResponseEntity<String> addStudent(@RequestBody Student student) {
        try {
            String output = adminService.addStudent(student);
            return ResponseEntity.status(201).body(output);
        } catch(Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
	}
	
	@PostMapping("/addteacher")
	public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher){
	try {
		String output =adminService.addTeacher(teacher);
		 return ResponseEntity.status(201).body(output);
	}catch(Exception e) {
		return ResponseEntity.status(500).body("Internal Server Error");
	
  }
	}
	
	 @GetMapping("/viewallstudents")
	    public ResponseEntity<?> viewAllStudents() {
	        try {
	            List<Student> students = adminService.viewAllStudents();
	            return ResponseEntity.ok(students);
	        } catch(Exception e) {
	            return ResponseEntity.status(500).body("Error Fetching Students");
	        }
	    }
	 
	 @GetMapping("/viewallteachers")
	   public ResponseEntity<?> viwAllTeachers(){
		 try {
			List<Teacher> teachers =adminService.viewAllTeachers();
			return ResponseEntity.ok(teachers);
		 }catch(Exception e) {
			 return ResponseEntity.status(500).body("Error Fetching Teachers");
		 } 
	 }
	 
	 @DeleteMapping("/deletestudent")
	 public ResponseEntity<String> deleteStudent(@RequestParam int id){
		 try {
			  boolean deleted = adminService.deleteStudent(id);
	            if(deleted) {
	                return ResponseEntity.ok("Student Deleted Successfully");
	            } else {
	                return ResponseEntity.status(404).body("Student Not Found");
	            }
	        } catch(Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
		 }
	 
	 @DeleteMapping("/deleteteacher")
	 public ResponseEntity<String> deleteteacher(@RequestParam int id){
		 try {
			 boolean deleted = adminService.deleteTeacher(id);
			 if(deleted) {
				 return ResponseEntity.ok("Teacher Deleted Successfully");
			 }else {
			return ResponseEntity.status(400).body("Teacher Not Found");	 
			 }
		 }catch(Exception e) {
			 return ResponseEntity.status(500).body("Internal Server Error");
		 }
	 }
	 
	 @GetMapping("/totalstudents")
	    public ResponseEntity<?> totalStudents() {
	        try {
	            long count = adminService.totalStudents();
	            return ResponseEntity.ok(count);
	        } catch(Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
	    }

	    @GetMapping("/totalteachers")
	    public ResponseEntity<?> totalTeachers() {
	        try {
	            long count = adminService.totalTeachers();
	            return ResponseEntity.ok(count);
	        } catch(Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
	    }
	    
	    @PostMapping("/addsubject")
	    public ResponseEntity<String> addSubject(@RequestBody Subject subject){
	    	try {
	    		String output = adminService.addSubject(subject);
	    		return ResponseEntity.status(200).body(output);
	    	}
	    	
	    catch(Exception e) {
	    	return ResponseEntity.status(501).body("Internal Server Error");
	    }
	  }
	    
	    @GetMapping("/viewallsubjects")
	    public ResponseEntity<?> viewAllSubjects(){
	    	try {
	    		List<Subject> subject=adminService.viewAllSubjects();
	    		return ResponseEntity.ok().body(subject);  
	    	}catch(Exception e) {
	    	return ResponseEntity.status(501).body("Internal Server Error");
	    }
	  }
	    
	    @DeleteMapping("/deletesubject")
	    public ResponseEntity<String> deleteSubject(@RequestParam String coursecode) {
	    	 try {
	    	     boolean deleted = adminService.deleteSubject(coursecode);
	    	      if(deleted) {
	    	      return ResponseEntity.ok("Subject Deleted Successfully");
	    	      } else {
	    	      return ResponseEntity.status(404).body("Subject Not Found");
	    	           }
	    	      } catch(Exception e) {
	    	        return ResponseEntity.status(500).body("Internal Server Error");
	    	        }   	
	           }
	}
	    
	 

