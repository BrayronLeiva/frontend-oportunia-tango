package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.domain.model.RatingCompanyStudent

interface RatingCompanyStudentRepository {
    suspend fun findAllRatingCompanyStudents(): Result<List<RatingCompanyStudent>>
    suspend fun findRatingCompanyStudentById(id: Long): Result<RatingCompanyStudent>
    suspend fun saveRatingCompanyStudent(ratingCompanyStudent: RatingCompanyStudent): Result<Unit>
    suspend fun deleteRatingCompanyStudent(id: Long): Result<Unit>
    suspend fun updateRatingCompanyStudent(ratingCompanyStudent: RatingCompanyStudent): Result<Unit>
}