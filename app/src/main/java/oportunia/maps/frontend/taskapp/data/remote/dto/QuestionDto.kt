package oportunia.maps.frontend.taskapp.data.remote.dto

/**
 * This class represents the Dto of a Question.
 * @property id The unique identifier of the question.
 * @property question The interrogative of the question.
 * @property answer The answer of the question.
 * @property company The company of the question.
 */

data class QuestionDto(
    val id: Long,
    val question: String,
    val answer: String,
    val company: CompanyDto
)
