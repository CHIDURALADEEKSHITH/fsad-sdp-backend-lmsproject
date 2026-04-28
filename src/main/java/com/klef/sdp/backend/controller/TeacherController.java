package com.klef.sdp.backend.controller;

import java.awt.PageAttributes.MediaType;
import java.net.http.HttpHeaders;
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
import org.springframework.web.multipart.MultipartFile;

import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.entity.Submission;
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
            
            if (output.contains("Successfully")) {
                return ResponseEntity.ok(output);
            } else if (output.contains("Error")) {
                return ResponseEntity.status(500).body(output);
            } else {
                return ResponseEntity.status(400).body(output);
            }
        } catch(Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
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
	  
	  @GetMapping("/viewsubmissionsbyproject")
	  public ResponseEntity<?> viewSubmissionsByProject(@RequestParam int projectId) {
	      try {
	          List<Submission> submissions = teacherService.viewSubmissionsByProject(projectId);
	          return ResponseEntity.ok(submissions);
	      } catch(Exception e) {
	          return ResponseEntity.status(500).body("Internal Server Error");
	      }
	  }
	  
	  @PostMapping("/evaluatesubmission")
	  public ResponseEntity<?> evaluateSubmission(@RequestParam int submissionId, @RequestParam int marks, @RequestParam String feedback, @RequestParam int teacherId) {
	      try {
	          String output = teacherService.evaluateSubmission(submissionId, marks, feedback, teacherId);
	          return ResponseEntity.ok(output);
	      } catch(Exception e) {
	          return ResponseEntity.status(500).body("Internal Server Error");
	      }
	  }
	  
	  @GetMapping("/downloadsubmission")
	  public ResponseEntity<?> downloadSubmission(@RequestParam int submissionId) {
	      try {
	          Submission submission = teacherService.getSubmissionById(submissionId);
	          if (submission == null) {
	              return ResponseEntity.status(404).body("Submission not found");
	          }
	          
	          java.io.File file = new java.io.File(submission.getFilePath());
	          if (!file.exists()) {
	              return ResponseEntity.status(404).body("File not found on server");
	          }
	          
	          byte[] fileData = java.nio.file.Files.readAllBytes(file.toPath());
	          
	          org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
	          headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
	          headers.setContentDispositionFormData("attachment", submission.getFileName());
	          headers.setContentLength(fileData.length);
	          
	          return ResponseEntity.ok()
	                  .headers(headers)
	                  .body(fileData);
	      } catch(Exception e) {
	          e.printStackTrace();
	          return ResponseEntity.status(500).body("Internal Server Error");
	      }
	  }
	  @PostMapping("/addprojectwithfile")
	  public ResponseEntity<?> addProjectWithFile(
	          @RequestParam String title,
	          @RequestParam String description,
	          @RequestParam String deadline,
	          @RequestParam String coursecode,
	          @RequestParam int teacherId,
	          @RequestParam(required = false) MultipartFile file) {

	      try {
	          Project project = new Project();
	          project.setTitle(title);
	          project.setDescription(description);
	          project.setDeadline(java.time.LocalDate.parse(deadline));

	          String output = teacherService.addProjectWithFile(project, coursecode, teacherId, file);

	          if (output.contains("Successfully")) {
	              return ResponseEntity.ok(output);
	          } else {
	              return ResponseEntity.status(400).body(output);
	          }

	      } catch (Exception e) {
	          return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
	      }
	  }
	 
	  @GetMapping("/downloadprojectfile")
	  public ResponseEntity<?> downloadProjectFile(@RequestParam int projectId) {
	      try {
	          Project project = teacherService.getProjectById(projectId);

	          if (project == null) {
	              return ResponseEntity.status(404).body("Project not found");
	          }

	          byte[] fileData = teacherService.downloadProjectFile(projectId);

	          if (fileData == null) {
	              return ResponseEntity.status(404).body("File not found");
	          }

	          org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
	          headers.add("Content-Type", "application/pdf");
	          headers.add("Content-Disposition", "attachment; filename=\"" + project.getFileName() + "\"");

	          return ResponseEntity.ok()
	                  .headers(headers)
	                  .body(fileData);

	      } catch (Exception e) {
	          return ResponseEntity.status(500).body("Internal Server Error");
	      }
	  }
	
}
