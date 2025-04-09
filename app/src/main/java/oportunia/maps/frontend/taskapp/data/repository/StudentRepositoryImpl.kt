package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.mapper.QualificationMapper
import oportunia.maps.frontend.taskapp.data.mapper.StudentMapper
import oportunia.maps.frontend.taskapp.data.remote.QualificationRemoteDataSource
import oportunia.maps.frontend.taskapp.data.remote.StudentRemoteDataSource
import oportunia.maps.frontend.taskapp.domain.model.Qualification
import oportunia.maps.frontend.taskapp.domain.model.Student
import oportunia.maps.frontend.taskapp.domain.repository.QualificationRepository
import oportunia.maps.frontend.taskapp.domain.repository.StudentRepository
import oportunia.maps.frontend.taskapp.domain.repository.TaskRepository
import java.net.UnknownHostException
import javax.inject.Inject


/**
 * Implementation of [StudentRepository] that handles task data operations.
 * Provides error handling and mapping between data and domain layers.
 *
 * @property dataSource The data source for task operations
 * @property studentMapper The mapper for converting between domain and data layer task objects
 */
class StudentRepositoryImpl @Inject constructor(
    private val dataSource: StudentRemoteDataSource,
    private val studentMapper: StudentMapper
) : StudentRepository {

    /**
     * Retrieves all tasks from the data source.
     *
     * @return [Result] containing a list of qualifications if successful, or an error if the operation failed
     */


    override suspend fun findAllStudents(): Result<List<Student>> {
        return try {
            dataSource.getAll().map { dtos ->
                dtos.map { studentMapper.mapToDomain(it) }
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Please check your connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching location companies: ${e.message}"))
        }
    }
    /**
     * Finds a task by its ID.
     *
     * @param studentId The ID of the qualification to find
     * @return [Result] containing the qualification if found, or an error if the operation failed

     */


    /**
     * Retrieves a location company by its ID.
     */
    override suspend fun findStudentById(studentId: Long): Result<Student> {
        return dataSource.getById(studentId).map {
            studentMapper.mapToDomain(it)
        }
    }

    override suspend fun saveStudent(student: Student): Result<Unit> {
        return dataSource.create(studentMapper.mapToDto(student)).map {
            studentMapper.mapToDomain(it)
        }
    }

    override suspend fun updateStudent(student: Student): Result<Unit> {
        return dataSource.update(student.id, studentMapper.mapToDto(student)).map {
            studentMapper.mapToDomain(it)
        }
    }

    override suspend fun deleteStudent(studentId: Long): Result<Unit> {
        return dataSource.delete(studentId)
    }


}