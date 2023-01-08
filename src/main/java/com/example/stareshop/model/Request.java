package com.example.stareshop.model;

import jakarta.persistence.*;
import lombok.*;

import javax.naming.Name;
import java.util.Set;
@Entity
@Table(name = "request", schema = "public")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "isActive", nullable = false)
    private Boolean isActive;
}
