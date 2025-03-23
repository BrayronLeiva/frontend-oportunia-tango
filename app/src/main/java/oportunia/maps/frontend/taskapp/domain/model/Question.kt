package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents a question to a company.
 * @property id The unique identifier of the question.
 * @property question The interrogative of the question.
 * @property answer The answer of the question.
 * @property company The company of the question.
 */

data class Question(
    val id: Long,
    val question: String,
    val answer: String,
    val company: Company
)
