package com.schoolinformer.model.entity

import com.schoolinformer.model.Audit
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "application_version")
data class ApplicationVersion(
    @Column(unique = true)
    val applicationVersion: String = "0",
    val description: String = ""
) : Audit()