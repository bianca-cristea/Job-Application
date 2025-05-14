//package com.cristeabianca.job_application.user;
//
//import com.cristeabianca.job_application.application.Application;
//import com.cristeabianca.job_application.review.Review;
//import com.cristeabianca.job_application.role.Role;
//import jakarta.persistence.*;
//
//import java.util.List;
//import java.util.Set;
//
//@Entity
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String username;
//
//    @Column(nullable = false)
//    private String password;
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<Role> roles;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Application> applications;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Review> reviews;
//}
