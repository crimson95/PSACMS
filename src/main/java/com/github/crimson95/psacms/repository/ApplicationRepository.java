package com.github.crimson95.psacms.repository;

import com.github.crimson95.psacms.entity.Application;
import com.github.crimson95.psacms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    // Basic CRUD methods are inherited from JpaRepository.
    // Spring Data JPA builds the query automatically from the method name:
    // findByCurrentStatus(...) means "select applications where currentStatus matches the given value".
    List<Application> findByCurrentStatus(String currentStatus);

    // Spring Data also understands nested entity fields in method names.
    // This means "select applications for this applicant, newest first".
    List<Application> findByApplicantOrderByCreatedAtDesc(User applicant);
}
