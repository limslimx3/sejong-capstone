package com.sejong.capstone.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {

    @Column(name = "insertdate")
    private LocalDateTime createdAt;

    @Column(name = "updatedate")
    private LocalDateTime updatedAt;
}
