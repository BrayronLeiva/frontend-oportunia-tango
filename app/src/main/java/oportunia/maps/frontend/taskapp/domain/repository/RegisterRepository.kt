package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterCompanyCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterStudentCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterStudentDto
import java.io.File

interface RegisterRepository{
    suspend fun saveStudent(student: RegisterStudentCreateDto): Result<RegisterStudentDto>
    suspend fun saveCompany(company: RegisterCompanyCreateDto): Result<RegisterCompanyDto>
    suspend fun uploadProfileImageStudent(studentId: Long, file: File): Result<Map<String, String>>
    suspend fun uploadProfileImageCompany(companyId: Long, file: File): Result<Map<String, String>>
}