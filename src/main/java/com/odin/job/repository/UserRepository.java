package com.odin.job.repository;

import com.odin.job.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByName(String name);
    User findByEmail(String email);
}
