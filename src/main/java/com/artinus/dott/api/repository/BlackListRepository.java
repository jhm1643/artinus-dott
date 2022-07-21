package com.artinus.dott.api.repository;

import com.artinus.dott.api.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
}
