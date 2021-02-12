package com.michaelmartins.dscatalog.repositories;

import com.michaelmartins.dscatalog.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
