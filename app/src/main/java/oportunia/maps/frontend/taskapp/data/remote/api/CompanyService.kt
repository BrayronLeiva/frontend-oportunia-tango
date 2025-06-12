package oportunia.maps.frontend.taskapp.data.remote.api

import okhttp3.MultipartBody
import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyRequestDto
import retrofit2.Response
import retrofit2.http.*

interface CompanyService {

    /**
     * Retrieves all companies from the remote API.
     *
     * @return [Response] containing a list of [CompanyDto] objects if successful
     */
    @GET("/v1/companies")
    suspend fun getAllCompanies(): Response<List<CompanyDto>>

    /**
     * Retrieves a specific company by its unique identifier.
     *
     * @param id The unique identifier of the company to retrieve
     * @return [Response] containing the requested [CompanyDto] if successful
     */
    @GET("/v1/companies/{id}")
    suspend fun getCompanyById(@Path("id") id: Long): Response<CompanyDto>

    /**
     * Creates a new company entry in the remote API.
     *
     * @param company The [CompanyDto] object containing the data to create
     * @return [Response] containing the created [CompanyDto] with server-assigned ID if successful
     */
    @POST("/v1/companies")
    suspend fun createCompany(@Body company: CompanyRequestDto): Response<CompanyDto>

    /**
     * Updates an existing company entry in the remote API.
     *
     * @param id The unique identifier of the company to update
     * @param company The [CompanyDto] object containing the updated data
     * @return [Response] containing the updated [CompanyDto] if successful
     */
    @PUT("/v1/companies/{id}")
    suspend fun updateCompany(
        @Path("id") id: Long,
        @Body company: CompanyDto
    ): Response<CompanyDto>

    /**
     * Deletes a company entry from the remote API.
     *
     * @param id The unique identifier of the company to delete
     * @return [Response] indicating the success of the operation
     */
    @DELETE("/v1/companies/{id}")
    suspend fun deleteCompany(@Path("id") id: Long): Response<Unit>

    @GET("/v1/companies/me")
    suspend fun loggedCompany(): Response<CompanyDto>

    @Multipart
    @POST("/v1/companies/{id}/upload-image")
    suspend fun uploadProfileImage(
        @Path("id") id: Long,
        @Part file: MultipartBody.Part
    ): Response<Map<String, String>>
}