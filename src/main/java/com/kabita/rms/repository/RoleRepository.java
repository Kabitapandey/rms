package com.kabita.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabita.rms.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
