package com.klef.sdp.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    List<Submission> findByProjectGroup(ProjectGroup projectGroup);
    
    List<Submission> findByProjectGroupId(int projectGroupId);
    
    List<Submission> findBySubmittedById(int studentId);
    
    List<Submission> findByProjectGroupProjectId(int projectId);
}