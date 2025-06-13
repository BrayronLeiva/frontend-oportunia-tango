package oportunia.maps.frontend.taskapp.data.remote.api

import okhttp3.MultipartBody
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterCompanyCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterStudentCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterStudentDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RegisterService {
    @POST("/v1/unsecure/register/student")
    suspend fun registerStudent(@Body student: RegisterStudentCreateDto): Response<RegisterStudentDto>
    @POST("/v1/unsecure/register/company")
    suspend fun registerCompany(@Body company: RegisterCompanyCreateDto): Response<RegisterCompanyDto>

    @Multipart
    @POST("/v1/unsecure/register/student/{id}/upload-image")
    suspend fun uploadProfileImageStudent(
        @Path("id") id: Long,
        @Part file: MultipartBody.Part
    ): Response<Map<String, String>>

    @Multipart
    @POST("/v1/unsecure/register/company/{id}/upload-image")
    suspend fun uploadProfileImageCompany(
        @Path("id") id: Long,
        @Part file: MultipartBody.Part
    ): Response<Map<String, String>>
}