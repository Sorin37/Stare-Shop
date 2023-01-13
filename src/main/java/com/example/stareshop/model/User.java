package com.example.stareshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "users", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "role", nullable = false)
    private String role;

    @Transient
    private String passwordConfirm;

    @OneToOne(targetEntity = Business.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "businessId", referencedColumnName = "id")
    private Business businesses;
}
