package com.schoolinformer.model.entity

import com.schoolinformer.model.dto.FeedbackDTO
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "feedback_content")
@EntityListeners(AuditingEntityListener::class)
data class FeedbackContent(var feedbackContent: String? = "") {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0L

    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null

    @ManyToOne
    @JoinColumn(name = "feedback_provider_id")
    var feedbackProvider: FeedbackProvider? = null
}