package oportunia.maps.frontend.taskapp.data.datasource.qualification

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.data.mapper.QualificationMapper

class QualificationDataSourceImpl(
    private val qualificationMapper: QualificationMapper
) : QualificationDataSource {

    override suspend fun getQualifications(): Flow<List<QualificationDto>> = flow {
        val qualifications = QualificationProvider.findAllQualifications()
        emit(qualifications.map { qualificationMapper.mapToDto(it) })
    }

    override suspend fun getQualificationById(id: Long): QualificationDto? =
        QualificationProvider.findQualificationById(id)?.let { qualificationMapper.mapToDto(it) }

    override suspend fun insertQualification(qualificationDto: QualificationDto) {
        // Mock implementation - no persistence needed
    }

    override suspend fun updateQualification(qualificationDto: QualificationDto) {
        // Mock implementation - no persistence needed
    }

    override suspend fun deleteQualification(qualificationDto: QualificationDto) {
        // Mock implementation - no persistence needed
    }
}