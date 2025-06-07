package oportunia.maps.frontend.taskapp.data.remote

import oportunia.maps.frontend.taskapp.data.remote.api.CompanyService
import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyDto
import retrofit2.Response
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
    suspend fun create(dto: CompanyDto): Result<CompanyDto> = safeApiCall {
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