package com.artinus.dott.api.entity;

import com.artinus.dott.api.dto.type.RoleType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleType roleName;

    @ManyToMany(mappedBy="roles")
    private List<Users> users;
}
