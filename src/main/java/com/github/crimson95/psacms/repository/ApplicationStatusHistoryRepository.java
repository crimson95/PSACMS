package com.github.crimson95.psacms.repository;

import com.github.crimson95.psacms.entity.ApplicationStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStatusHistoryRepository extends JpaRepository<ApplicationStatusHistory, Long> {
    // Basic CRUD methods are inherited from JpaRepository.
}
