package com.schoolinformer.model

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    val id: Long = 0L

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    val lastUpdatedAt: LocalDateTime = LocalDateTime.now()
}