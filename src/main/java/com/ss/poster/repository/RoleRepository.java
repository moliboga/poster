package com.ss.poster.repository;

import java.util.Optional;

import com.ss.poster.model.ERole;
import com.ss.poster.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}