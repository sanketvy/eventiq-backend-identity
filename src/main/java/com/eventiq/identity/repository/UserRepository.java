package com.eventiq.identity.repository;

import com.eventiq.identity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
