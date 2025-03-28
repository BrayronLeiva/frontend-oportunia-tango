package oportunia.maps.frontend.taskapp.data.datasource

import kotlinx.coroutines.flow.Flow
import oportunia.maps.frontend.taskapp.data.datasource.model.QualificationDto
import oportunia.maps.frontend.taskapp.data.datasource.model.TaskDto
import oportunia.maps.frontend.taskapp.domain.model.Qualification

interface QualificationDataSource {
    suspend fun getQualifications(): Flow<List<QualificationDto>>
    suspend fun getQualificationById(id: Long): QualificationDto?
    suspend fun insertQualification(qualificationDto: QualificationDto)
    suspend fun updateQualification(qualificationDto: QualificationDto)
    suspend fun deleteQualification(qualificationDto: QualificationDto)
}