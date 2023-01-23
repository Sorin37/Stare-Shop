package com.example.stareshop.repository;

import com.example.stareshop.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET role = ?2, business_id = ?3 WHERE id = ?1", nativeQuery = true)
    void updateRole(Long id, String role, Long business_id);
}
