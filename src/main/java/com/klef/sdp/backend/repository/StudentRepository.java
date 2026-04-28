package com.klef.sdp.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.klef.sdp.backend.entity.ProjectGroup;
import com.klef.sdp.backend.entity.Student;
import jakarta.transaction.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByUsernameAndPassword(String username, String password);

    @Query("select s from Student s where s.email=?1 and s.password=?2")
    Student checkLogin(String email, String password);

    Student findByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.email=?1")
    Student getStudentByEmail(String email);

    List<Student> findByDepartment(String department);

    @Query("SELECT s FROM Student s WHERE s.department=?1")
    List<Student> getStudentsByDepartment(String department);

    @Query("SELECT COUNT(s) FROM Student s")
    long totalStudents();

    long countByDepartment(String department);

    boolean existsByEmail(String email);

    boolean existsByContact(String contact);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.password=?2 WHERE s.email=?1")
    int updatePasswordByEmail(String email, String password);

    @Modifying
    @Transactional
    @Query("DELETE FROM Student s WHERE s.email=?1")
    int deleteStudentByEmail(String email);
    
    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.group.id=?2 WHERE s.id=?1")
    int joinGroup(int studentId, int groupId);
    
    @Query("SELECT COUNT(s) FROM Student s WHERE s.id=?1 AND s.group IS NOT NULL")
    long checkStudentInGroup(int studentId);
    
    @Query("SELECT s FROM Student s WHERE s.group.id=?1")
    List<Student> getStudentsByGroupId(int groupId);
    
    @Query("SELECT s FROM Student s JOIN s.groups g WHERE g.id=?1")
    List<Student> getStudentsByGroupIdNew(int groupId);
   
    @Query("SELECT COUNT(s) FROM Student s WHERE s.group.project.id = :projectId AND s.id = :studentId")
    int checkStudentInGroupForProject(@Param("studentId") int studentId, @Param("projectId") int projectId);
}