package oportunia.maps.frontend.taskapp.data.datasource.qualification

import kotlinx.coroutines.flow.Flow
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto

interface QualificationDataSource {
    suspend fun getQualifications(): Flow<List<QualificationDto>>
    suspend fun getQualificationById(id: Long): QualificationDto?
    suspend fun insertQualification(qualificationDto: QualificationDto)
    suspend fun updateQualification(qualificationDto: QualificationDto)
    suspend fun deleteQualification(qualificationDto: QualificationDto)
}