package com.schoolinformer.repository

import com.schoolinformer.model.entity.ApplicationVersion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ApplicationVersionRepository : JpaRepository<ApplicationVersion, Long> {
    @Query(
        value = "SELECT * FROM application_version av ORDER BY av.created_at DESC LIMIT 1",
        nativeQuery = true
    )
    fun findLatestVersion(): Optional<ApplicationVersion>
}