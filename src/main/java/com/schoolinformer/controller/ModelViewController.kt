package com.schoolinformer.controller

import com.schoolinformer.service.FeedbackService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/school-informer")
class ModelViewController(
    private val feedbackService: FeedbackService
) {
    @GetMapping("/feedback-display")
    fun displayFeedback(model: Model): String {
        val groupedFeedback = feedbackService.getAllFeedbackGroupedByProviders()
        model.addAttribute("groupedFeedback", groupedFeedback)
        return "feedback-display"
    }
}