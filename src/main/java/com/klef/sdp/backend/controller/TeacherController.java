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

import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.entity.Teacher;
import com.klef.sdp.backend.service.TeacherService;

@RestController
@RequestMapping("/teacherapi")
@CrossOrigin("*")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	
	@GetMapping("/")
	public String Teacherhome() {
		return "Teacher Demo";
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> verifyTeacherLogin(@RequestBody Teacher teacher){
		try {
			 Teacher t = teacherService.verifyTeacherLogin(teacher.getEmail(), teacher.getPassword());
			if(t!=null) {
				return ResponseEntity.status(200).body(t);
			}else {
				return ResponseEntity.status(401).body("Invalid login");
			}
		}catch(Exception e) {
				return ResponseEntity.status(500).body("Internal Server Error");
			}
	}
	
	@PostMapping("/updateprofile")
	public ResponseEntity<?> updateTeacherProfile(@RequestBody Teacher teacher){
		try {
		String output = teacherService.updateTeacherProfile(teacher);
		return ResponseEntity.status(200).body(output);
		}catch(Exception e) {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	} 
	
	@GetMapping("/viewsubjectbydepartment")
	public ResponseEntity<?> viewSubjectsByDepartment(@RequestParam String department){
		try {
			List<Subject> output =teacherService.viewSubjectsByDepartment(department);
			return ResponseEntity.status(200).body(output);
		}catch(Exception e)  {
			return ResponseEntity.status(500).body("Internal Server Error");
		}
	}
	
	 @PostMapping("/addproject")
	    public ResponseEntity<String> addProject( @RequestBody Project project, @RequestParam String coursecode,@RequestParam int teacherId) {
	        try {
	            String output = teacherService.addProject(project, coursecode, teacherId);
	            return ResponseEntity.status(201).body(output);
	        } catch(Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
	    }
	 
	 @GetMapping("/viewprojectsbysubject")
	    public ResponseEntity<?> viewProjectsBySubject(@RequestParam String coursecode) {
	        try {
	            List<Project> projects = teacherService.viewProjectsBySubject(coursecode);
	            return ResponseEntity.ok(projects);
	        } catch(Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
	    }
	 
	  @DeleteMapping("/deleteproject")
	    public ResponseEntity<String> deleteProject(@RequestParam int projectId) {
	        try {
	            boolean deleted = teacherService.deleteProject(projectId);
	            if(deleted) {
	                return ResponseEntity.ok("Project Deleted Successfully");
	            } else {
	                return ResponseEntity.status(404).body("Project Not Found");
	            }
	        } catch(Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
	    }
	 
	  @PostMapping("/creategroup")
	    public ResponseEntity<String> createGroup( @RequestParam int projectId,@RequestParam int maxMembers) {
	        try {
	            String output = teacherService.createGroup(projectId, maxMembers);
	            return ResponseEntity.status(201).body(output);
	        } catch(Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
	    }
	  
	  @GetMapping("/viewgroupsbyproject")
	    public ResponseEntity<?> viewGroupsByProject(@RequestParam int projectId) {
	        try {
	            List<ProjectGroup> groups = teacherService.viewGroupsByProject(projectId);
	            return ResponseEntity.ok(groups);
	        } catch(Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
	    }
	  
	  @DeleteMapping("/deletegroup")
	    public ResponseEntity<String> deleteGroup(@RequestParam int groupId) {
	        try {
	            boolean deleted = teacherService.deleteGroup(groupId);
	            if(deleted) {
	                return ResponseEntity.ok("Group Deleted Successfully");
	            } else {
	                return ResponseEntity.status(404).body("Group Not Found");
	            }
	        } catch(Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
	    }
	  
	  @PostMapping("/assignleader")
	    public ResponseEntity<String> assignLeader( @RequestParam int groupId, @RequestParam int studentId) {
	        try {
	            String output = teacherService.assignLeader(groupId, studentId);
	            return ResponseEntity.status(200).body(output);
	        } catch(Exception e) {
	            return ResponseEntity.status(500).body("Internal Server Error");
	        }
	    }
	  
	  @GetMapping("/viewmembersbygroup")
	  public ResponseEntity<?> viewMembersByGroup(@RequestParam int groupId) {
	      try {
	          List<Student> members = teacherService.viewMembersByGroup(groupId);
	          return ResponseEntity.ok(members);
	      } catch(Exception e) {
	          return ResponseEntity.status(500).body("Internal Server Error");
	      }
	  }
	 
	
}
