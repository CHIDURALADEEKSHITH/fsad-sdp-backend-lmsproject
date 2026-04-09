package com.klef.sdp.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klef.sdp.backend.entity.Teacher;
import jakarta.transaction.Transactional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Teacher findByEmailAndPassword(String email, String password);

    @Query("select t from Teacher t where t.email=?1 and t.password=?2")
    Teacher checkLogin(String email, String password); 

    Teacher findByEmail(String email);

    @Query("SELECT t FROM Teacher t WHERE t.email=?1")
    Teacher getTeacherByEmail(String email);

    List<Teacher> findByDepartment(String department);

    @Query("SELECT t FROM Teacher t WHERE t.department=?1")
    List<Teacher> getTeachersByDepartment(String department);

    List<Teacher> findByDesignation(String designation);

    @Query("SELECT t FROM Teacher t WHERE t.designation=?1")
    List<Teacher> getTeachersByDesignation(String designation);

    @Query("SELECT COUNT(t) FROM Teacher t")
    long totalTeachers();

    long countByDepartment(String department);

    boolean existsByEmail(String email);

    boolean existsByContact(String contact);

    @Modifying
    @Transactional
    @Query("UPDATE Teacher t SET t.password=?2 WHERE t.email=?1")
    int updatePasswordByEmail(String email, String password); 

    @Modifying
    @Transactional
    @Query("DELETE FROM Teacher t WHERE t.email=?1")
    int deleteTeacherByEmail(String email);
}