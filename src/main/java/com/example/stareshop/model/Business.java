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

    @OneToMany(targetEntity = Request.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "b2bid", referencedColumnName = "id")
    private Set<Request> b2b_requests;

    @OneToMany(targetEntity = Request.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "b2cid", referencedColumnName = "id")
    private Set<Request> b2c_requests;
}
