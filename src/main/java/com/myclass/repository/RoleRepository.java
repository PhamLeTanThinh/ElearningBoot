package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myclass.entity.Role;
import com.myclass.entity.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {


}
