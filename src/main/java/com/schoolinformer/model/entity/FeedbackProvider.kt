package com.schoolinformer.model.entity

import com.schoolinformer.model.dto.FeedbackDTO
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "feedback_provider")
@EntityListeners(AuditingEntityListener::class)
data class FeedbackProvider(val feedbackProviderName: String? = "", val providerEmail: String? = "") {

    constructor(feedbackDTO: FeedbackDTO) :
            this(feedbackProviderName = feedbackDTO.name, providerEmail = feedbackDTO.email)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0L

    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime? = LocalDateTime.now()

    @OneToMany(mappedBy = "feedbackProvider", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var feedbacks: MutableList<FeedbackContent> = mutableListOf()
    override fun toString(): String {
        return "FeedbackProvider(feedbackProviderName=$feedbackProviderName, providerEmail=$providerEmail, id=$id, createdAt=$createdAt)"
    }


}