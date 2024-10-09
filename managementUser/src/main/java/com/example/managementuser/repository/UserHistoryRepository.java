package com.example.managementuser.repository;

import com.example.managementuser.entity.UserHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistoryEntity, Long> {
}