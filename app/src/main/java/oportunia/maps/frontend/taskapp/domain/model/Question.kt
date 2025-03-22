package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents a question to a company.
 * @property id The unique identifier of the question.
 * @property question The interrogative of the question.
 * @property answer The answer of the question.
 * @property company The company of the question.
 */

data class Question(
    var id: Long,
    var question: String,
    var answer: String,
    var company: Company
)
