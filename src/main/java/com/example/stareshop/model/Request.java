package com.example.stareshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "requests", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "b2c_id")
    private Business b2c;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @Basic
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Basic
    @Column(name = "is_accepted", nullable = false)
    private boolean isAccepted;
}
