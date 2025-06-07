package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.data.remote.dto.RequestCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestUpdateDto
import oportunia.maps.frontend.taskapp.domain.model.Request

interface RequestRepository {
    suspend fun findAllRequests(): Result<List<Request>>
    suspend fun findRequestById(requestId: Long): Result<Request>
    suspend fun saveRequest(request: RequestCreateDto): Result<Request>
    //suspend fun saveRequest(request: Request): Result<Request>
    suspend fun deleteRequest(requestId: Long): Result<Unit>
    suspend fun updateRequest(request: RequestUpdateDto): Result<Request>
    suspend fun findByStudentAndCompany(studentId: Long): Result<List<Request>>

}