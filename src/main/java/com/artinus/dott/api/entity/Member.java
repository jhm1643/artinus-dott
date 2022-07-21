package com.artinus.dott.api.entity;

import com.artinus.dott.api.dto.type.RoleType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private LocalDateTime updateDt;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
