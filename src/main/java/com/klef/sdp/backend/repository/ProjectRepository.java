package com.klef.sdp.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klef.sdp.backend.entity.Project;
import com.klef.sdp.backend.entity.Subject;
import com.klef.sdp.backend.entity.Teacher;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findBySubject(Subject subject);

    @Query("SELECT p FROM Project p WHERE p.subject=?1")
    List<Project> getProjectsBySubject(Subject subject);

   
    List<Project> findByTeacher(Teacher teacher); //

    @Query("SELECT p FROM Project p WHERE p.teacher=?1")
    List<Project> getProjectsByTeacher(Teacher teacher);

    long countByTeacher(Teacher teacher);

    @Query("SELECT COUNT(p) FROM Project p")
    long totalProjects();
}