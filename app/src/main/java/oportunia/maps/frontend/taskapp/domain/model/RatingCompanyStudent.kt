package oportunia.maps.frontend.taskapp.domain.model

import oportunia.maps.frontend.taskapp.data.datasource.model.enumClasses.TypeUser

/**
 * This class represents the ratings of a student or a company.
 * @property id The unique identifier of the rating.
 * @property rating The score of the rating.
 * @property type The type of the rating.
 * @property comment The comment of the rating.
 * @property student The student of the rating.
 * @property company The company of the rating.
 */


data class RatingCompanyStudent(
    var id: Long,
    var rating: Double,
    var type: TypeUser,
    var comment: String,
    var student: Student,
    var company: Company
)
