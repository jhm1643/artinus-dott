package com.artinus.dott.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "black_list_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BlackListUser {

    @Id
    private String email;

    @Column
    private String accessToken;
}
