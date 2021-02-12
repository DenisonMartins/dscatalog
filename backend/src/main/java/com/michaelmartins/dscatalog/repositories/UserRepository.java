package com.michaelmartins.dscatalog.repositories;

import com.michaelmartins.dscatalog.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
