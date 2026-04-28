package com.klef.sdp.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;

import jakarta.transaction.Transactional;

@Repository
public interface ProjectGroupRepository extends JpaRepository<ProjectGroup, Integer> {

 
    List<ProjectGroup> findByProject(Project project);

    @Query("SELECT g FROM ProjectGroup g WHERE g.project=?1")
    List<ProjectGroup> getGroupsByProject(Project project);

    ProjectGroup findByLeader(Student leader);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.group=?1")
    long countMembersByGroup(ProjectGroup group); 

    @Query("SELECT COUNT(m) FROM ProjectGroup g JOIN g.members m WHERE g=?1")
    long countMembersByGroupNew(ProjectGroup group); 

    @Query("SELECT COUNT(s) FROM Student s WHERE s.group IN (SELECT g FROM ProjectGroup g WHERE g.project=?1) AND s.id=?2")
    long checkStudentInProject(Project project, int studentId);
    
    @Modifying
    @Transactional
    @Query("UPDATE ProjectGroup g SET g.leader=?2 WHERE g.id=?1")
    int assignLeader(int groupId, Student leader);
    
    @Query("SELECT g FROM ProjectGroup g WHERE g.project.id=?1")
    List<ProjectGroup> findByProjectId(int projectId);
    
    @Query("SELECT g FROM ProjectGroup g JOIN g.members m WHERE m.id=?1")
    List<ProjectGroup> findByStudentId(int studentId);
    
    @Query("SELECT COUNT(g) FROM ProjectGroup g JOIN g.members m WHERE m.id=?1 AND g.project.id=?2")
    long checkStudentInProject(int studentId, int projectId);
}