package com.klef.sdp.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.sdp.backend.entity.Subject;

import jakarta.transaction.Transactional;
@Repository
public interface SubjectRepository extends JpaRepository<Subject, String>
{
	    List<Subject> findByDepartment(String department);
	 
	    @Query("SELECT s FROM Subject s WHERE s.department = ?1")
	    List<Subject> getSubjectsByDepartment(String department);
	 
	    @Query("SELECT COUNT(s) FROM Subject s")
	    long totalSubjects(); 
	  
	    @Modifying
	    @Transactional
	    @Query("DELETE FROM Subject s WHERE s.coursecode=?1")
	    int deleteSubjectByCoursecode(String coursecode);
}
