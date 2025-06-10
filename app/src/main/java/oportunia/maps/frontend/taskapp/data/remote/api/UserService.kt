package oportunia.maps.frontend.taskapp.data.remote.api

import oportunia.maps.frontend.taskapp.data.remote.dto.UserCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @GET("v1/users")
    suspend fun getAllUsers(): Response<List<UserDto>>

    @GET("v1/users/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<UserDto>

    @POST("v1/users")
    suspend fun createUser(@Body user: UserDto): Response<UserDto>

    @POST("v1/users")
    suspend fun createUser(@Body user: UserCreateDto): Response<UserDto>

    @PUT("v1/users/{id}")
    suspend fun updateUser(
        @Path("id") id: Long,
        @Body user: UserDto
    ): Response<UserDto>

    @DELETE("v1/users/{id}")
    suspend fun deleteUser(@Path("id") id: Long): Response<Unit>
}