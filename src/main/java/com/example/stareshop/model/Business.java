package com.example.stareshop.model;

import jakarta.persistence.*;
import lombok.*;

import javax.naming.Name;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "businesses", schema = "public")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Basic
    @Column(name = "address", nullable = false)
    private String address;
    @Basic
    @Column(name = "token", nullable = false)
    private String token;
    @Basic
    @Column(name = "type", nullable = false)
    private String type;
    @Basic
    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Inventory> inventory;

//    @OneToMany(targetEntity = Business.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "adminId", referencedColumnName = "id")
//    private List<Business> businesses;

    @ManyToOne
    @JoinColumn(name = "b2cid")
    private Business b2cid;
    @ManyToOne
    @JoinColumn(name = "b2bid")
    private Business b2bid;
}
