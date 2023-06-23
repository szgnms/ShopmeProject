package com.shopme.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(length = 128, nullable = false, unique = true)
    private String email;
    @NonNull
    @Column(length = 64, nullable = false)
    private String password;
    @NonNull
    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;
    @NonNull
    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;
    @Column(length = 64)
    private String photos;

    private boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =@JoinColumn(name = "role_id")
            )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
    @Transient
    public String getPhotosImagePath() {
        if (id==null|| photos==null) return "/images/defUser.png";
        return "/user-photos/"+this.id+"/"+this.photos;
    }
}
