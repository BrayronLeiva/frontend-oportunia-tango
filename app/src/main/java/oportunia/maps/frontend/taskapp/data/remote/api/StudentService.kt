package oportunia.maps.frontend.taskapp.data.remote.api

import oportunia.maps.frontend.taskapp.data.remote.dto.StudentCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentRecommendedDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface StudentService {

    /**
     * Retrieves all students entries from the remote API.
     *
     * @return [Response] containing a list of [StudentDto] objects if successful
     */
    @GET("/v1/students")
    suspend fun getAllstudents(): Response<List<StudentDto>>

    /**
     * Retrieves a specific student by its unique identifier.
     *
     * @param id The unique identifier of the student to retrieve
     * @return [Response] containing the requested [StudentDto] if successful
     */
    @GET("/v1/students/{id}")
    suspend fun getStudentById(@Path("id") id: Long): Response<StudentDto>

    /**
     * Creates a new student entry in the remote API.
     *
     * @param student The [StudentDto] object containing the data to create
     * @return [Response] containing the created [StudentDto] with server-assigned ID if successful
     */
    //@POST("/v1/students")
    //suspend fun createStudent(@Body student: StudentDto): Response<StudentDto>


    /**
     * Creates a new student entry in the remote API.
     *
     * @param student The [StudentDto] object containing the data to create
     * @return [Response] containing the created [StudentDto] with server-assigned ID if successful
     */
    @POST("/v1/students")
    suspend fun createStudent(@Body student: StudentCreateDto): Response<StudentDto>
    /**
     * Updates an existing student entry in the remote API.
     *
     * @param id The unique identifier of the student to update
     * @param student The [StudentDto] object containing the updated data
     * @return [Response] containing the updated [StudentDto] if successful
     */
    @PUT("/v1/students/{id}")
    suspend fun updateStudent(
        @Path("id") id: Long,
        @Body student: StudentDto
    ): Response<StudentDto>

    /**
     * Deletes a student entry from the remote API.
     *
     * @param id The unique identifier of the location to delete
     * @return [Response] indicating the success of the operation
     */
    @DELETE("/v1/students/{id}")
    suspend fun deleteStudent(@Path("id") id: Long): Response<Unit>

    /**
     * Retrieves a specific student by its unique identifier.
     *
     * @param id The unique identifier of the student to retrieve
     * @return [Response] containing the requested [StudentDto] if successful
     */
    @GET("/v1/students/me")
    suspend fun getStudentByLoggedStudent(): Response<StudentDto>



    /**
     * Retrieves all students entries from the remote API.
     *
     * @return [Response] containing a list of [StudentDto] objects if successful
     */
    @GET("/v1/students/recommendations")
    suspend fun getRecommendedStudents(): Response<List<StudentRecommendedDto>>

    /**
     * Retrieves all students entries from the remote API.
     *
     * @return [Response] containing a list of [StudentDto] objects if successful
     */
    @GET("/v1/students/company/logged")
    suspend fun getStudentsRequestingMyCompany(): Response<List<StudentDto>>
}