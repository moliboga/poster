package com.ss.poster.repository;

import java.util.Optional;

import com.ss.poster.model.ERole;
import com.ss.poster.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(ERole name);
}