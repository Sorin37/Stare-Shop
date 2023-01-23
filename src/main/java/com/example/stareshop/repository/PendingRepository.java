package com.example.stareshop.repository;

import com.example.stareshop.model.Pending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingRepository extends JpaRepository<Pending, Long> {

}
