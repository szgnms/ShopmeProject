package com.shopme.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Table(name = "roles")
@EqualsAndHashCode(of = "id")

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @NonNull
    @Column(length = 40,nullable = false,unique = true)
    private String name;
    @NonNull
    @Column(length = 150,nullable = false)
    private String description;


    public Role(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
