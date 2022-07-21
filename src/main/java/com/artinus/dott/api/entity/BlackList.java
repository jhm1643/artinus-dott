package com.artinus.dott.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "black_list_token")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BlackList{

    @Id
    private Long memberId;
}
