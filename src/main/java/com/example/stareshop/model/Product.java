package com.example.stareshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "product", schema = "public")
@Data
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
