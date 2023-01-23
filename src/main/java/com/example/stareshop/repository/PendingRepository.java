package com.example.stareshop.repository;

import com.example.stareshop.model.Pending;
import com.example.stareshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PendingRepository extends JpaRepository<Pending, Long> {
    Optional<Pending> findByUser_Id(Long user_Id);
}
