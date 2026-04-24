package com.github.crimson95.psacms.repository;

import com.github.crimson95.psacms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // 告訴 Spring 這是一個負責資料庫溝通的元件
public interface UserRepository extends JpaRepository<User, Long> {
    // 繼承 JpaRepository 後，Spring 會自動幫你寫好 save(), findById(), delete() 等所有基礎 SQL 方法！
}
