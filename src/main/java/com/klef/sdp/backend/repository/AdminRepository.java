package com.klef.sdp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.sdp.backend.entity.Admin;


@Repository
public interface AdminRepository extends  JpaRepository<Admin, String>
{
	 Admin findByUsernameAndPassword(String username, String password);
}
