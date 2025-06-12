package oportunia.maps.frontend.taskapp.data.remote

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import oportunia.maps.frontend.taskapp.data.remote.api.CompanyService
import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyRequestDto
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class CompanyRemoteDataSource @Inject constructor(
    private val companyService: CompanyService
) {

    /**
     * Retrieves all companies from the remote API.
     */
    suspend fun getAll(): Result<List<CompanyDto>> = safeApiCall {
        companyService.getAllCompanies()
    }

    /**
     * Retrieves a specific company by its ID.
     */
    suspend fun getById(id: Long): Result<CompanyDto> = safeApiCall {
        companyService.getCompanyById(id)
    }

    /**
     * Creates a new company.
     */
    suspend fun create(dto: CompanyRequestDto): Result<CompanyDto> = safeApiCall {
        companyService.createCompany(dto)
    }

    /**
     * Updates an existing company.
     */
    suspend fun update(id: Long, dto: CompanyDto): Result<CompanyDto> = safeApiCall {
        companyService.updateCompany(id, dto)
    }

    /**
     * Deletes a company by its ID.
     */
    suspend fun delete(id: Long): Result<Unit> = safeApiCall {
        companyService.deleteCompany(id)
    }

    suspend fun loggedCompany(): Result<CompanyDto> = safeApiCall {
        companyService.loggedCompany()
    }

    suspend fun uploadProfileImage(companyId: Long, file: File): Result<Map<String, String>> = safeApiCall {
        val requestFile = file
            .asRequestBody("image/*".toMediaTypeOrNull()) // puedes ajustar MIME si es JPEG, PNG, etc.
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        companyService.uploadProfileImage(companyId, body)
    }

    /**
     * Helper function to handle API calls safely.
     */
    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let { Result.success(it) }
                ?: Result.failure(Exception("Response body was null"))
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("API error ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}