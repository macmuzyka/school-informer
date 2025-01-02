package com.schoolinformer.model.entity

import com.schoolinformer.model.dto.FeatureDTO
import com.schoolinformer.model.enums.DevelopmentStage
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table
@EntityListeners(AuditingEntityListener::class)
data class Feature(
    val description: String = "",
    @Enumerated(EnumType.STRING)
    var developmentStage: DevelopmentStage = DevelopmentStage.NOT_YET_DETERMINED
) {
    constructor(feature: FeatureDTO) : this(
        description = feature.description,
        developmentStage = feature.developmentStage
    )

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0L

    @Column(updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var lastUpdatedAt: LocalDateTime? = null

    override fun toString(): String {
        return "Feature(description='$description'," +
                " developmentStage=$developmentStage," +
                " id=$id, createdAt=$createdAt," +
                " lastUpdatedAt=$lastUpdatedAt)"
    }
}
