package com.github.crimson95.psacms.repository;

import com.github.crimson95.psacms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository  // Marks this interface as a persistence component.
public interface UserRepository extends JpaRepository<User, Long> {
    // By extending JpaRepository, Spring provides common CRUD methods
    // such as save(), findById(), findAll(), and deleteById().
    Optional<User> findByUsername(String username);
}
