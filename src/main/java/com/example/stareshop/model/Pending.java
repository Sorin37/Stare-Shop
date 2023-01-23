package com.example.stareshop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pending", schema = "public")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name="business_id")
    private Business business;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
