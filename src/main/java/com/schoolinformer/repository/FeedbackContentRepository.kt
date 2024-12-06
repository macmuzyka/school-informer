package com.schoolinformer.repository

import com.schoolinformer.model.entity.FeedbackContent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeedbackContentRepository : JpaRepository<FeedbackContent, Long>