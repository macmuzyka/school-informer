package com.schoolinformer.model.entity

import com.schoolinformer.model.dto.FeedbackDTO
import com.schoolinformer.model.dto.FeedbackDisplayDTO
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "feedback_provider")
@NamedNativeQuery(
    name = "FeedbackProvider.findFeedbackContentGroupedByProviders",
    query = "SELECT fp.provider_email, fc.feedback_content, fc.created_at " +
            "FROM feedback_provider fp " +
            "INNER JOIN feedback_content fc " +
            "ON fp.id = fc.feedback_provider_id",
    resultSetMapping = "FeedbackDisplayDTOMapping"
)
@SqlResultSetMapping(
    name = "FeedbackDisplayDTOMapping",
    classes = [
        ConstructorResult(
            targetClass = FeedbackDisplayDTO::class,
            columns = [
                ColumnResult(name = "provider_email", type = String::class),
                ColumnResult(name = "feedback_content", type = String::class),
                ColumnResult(name = "created_at", type = LocalDateTime::class)
            ]
        )
    ]
)
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

    @OneToMany(
        mappedBy = "feedbackProvider",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    var feedbacks: MutableList<FeedbackContent> = mutableListOf()
    override fun toString(): String {
        return "FeedbackProvider(feedbackProviderName=$feedbackProviderName, providerEmail=$providerEmail, id=$id, createdAt=$createdAt)"
    }
}