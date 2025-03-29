package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.domain.model.Qualification

interface QualificationRepository {

    suspend fun findAllQualifications(): Result<List<Qualification>>
    suspend fun findQualificationById(qualificationId: Long): Result<Qualification>
    suspend fun saveQualification(qualification: Qualification): Result<Unit>
    suspend fun deleteQualification(qualificationId: Long): Result<Unit>
    suspend fun updateQualification(qualification: Qualification): Result<Unit>
}