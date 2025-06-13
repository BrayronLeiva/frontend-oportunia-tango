package oportunia.maps.frontend.taskapp.data.remote

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import oportunia.maps.frontend.taskapp.data.remote.api.RegisterService
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterCompanyCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterStudentCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterStudentDto
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class RegisterRemoteDataSource @Inject constructor(
    private val registerService: RegisterService
) {
    suspend fun registerStudent(student: RegisterStudentCreateDto): Result<RegisterStudentDto> = safeApiCall {
        registerService.registerStudent(student)
    }

    suspend fun registerCompany(company: RegisterCompanyCreateDto): Result<RegisterCompanyDto> = safeApiCall {
        registerService.registerCompany(company)
    }

    suspend fun uploadProfileImageStudent(studentId: Long, file: File): Result<Map<String, String>> = safeApiCall {
        val requestFile = file
            .asRequestBody("image/*".toMediaTypeOrNull()) // puedes ajustar MIME si es JPEG, PNG, etc.
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        registerService.uploadProfileImageStudent(studentId, body)
    }

    suspend fun uploadProfileImageCompany(companyId: Long, file: File): Result<Map<String, String>> = safeApiCall {
        val requestFile = file
            .asRequestBody("image/*".toMediaTypeOrNull()) // puedes ajustar MIME si es JPEG, PNG, etc.
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        registerService.uploadProfileImageCompany(companyId, body)
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Response body was null"))
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("API error ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}