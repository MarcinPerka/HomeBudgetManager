package com.archu.homebudgetmanager.repository;

import com.archu.homebudgetmanager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByCode(String code);
}
