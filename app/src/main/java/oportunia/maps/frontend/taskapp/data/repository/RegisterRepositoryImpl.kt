package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.remote.RegisterRemoteDataSource
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterCompanyCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterStudentCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterStudentDto
import oportunia.maps.frontend.taskapp.domain.repository.RegisterRepository
import java.io.File
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val dataSource: RegisterRemoteDataSource,
): RegisterRepository {
    override suspend fun saveCompany(company: RegisterCompanyCreateDto): Result<RegisterCompanyDto> {
        return dataSource.registerCompany(company)
    }
    override suspend fun saveStudent(student: RegisterStudentCreateDto): Result<RegisterStudentDto> {
        return dataSource.registerStudent(student)
    }
    override suspend fun uploadProfileImageStudent(studentId: Long, file: File): Result<Map<String, String>> {
        return dataSource.uploadProfileImageStudent(studentId, file)
    }
    override suspend fun uploadProfileImageCompany(companyId: Long, file: File): Result<Map<String, String>> {
        return dataSource.uploadProfileImageCompany(companyId, file)
    }

}