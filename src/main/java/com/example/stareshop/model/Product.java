package com.example.stareshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "product", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Basic
    @Column(name = "producer")
    private String producer;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Inventory> inventory;
}
