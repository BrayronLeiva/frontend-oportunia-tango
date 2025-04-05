package oportunia.maps.frontend.taskapp.data.repository


import oportunia.maps.frontend.taskapp.domain.error.DomainError
import oportunia.maps.frontend.taskapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import oportunia.maps.frontend.taskapp.data.datasource.qualification.QualificationDataSource
import oportunia.maps.frontend.taskapp.data.mapper.QualificationMapper
import oportunia.maps.frontend.taskapp.domain.model.Qualification
import oportunia.maps.frontend.taskapp.domain.repository.QualificationRepository
import java.io.IOException

/**
 * Implementation of [TaskRepository] that handles task data operations.
 * Provides error handling and mapping between data and domain layers.
 *
 * @property dataSource The data source for task operations
 * @property taskMapper The mapper for converting between domain and data layer task objects
 */
class QualificationRepositoryImpl(
    private val dataSource: QualificationDataSource,
    private val qualificationMapper: QualificationMapper
) : QualificationRepository {

    /**
     * Retrieves all tasks from the data source.
     *
     * @return [Result] containing a list of qualifications if successful, or an error if the operation failed
     * @throws DomainError.NetworkError if there's a network-related issue
     * @throws DomainError.MappingError if there's an error mapping the data
     * @throws DomainError.UnknownError for unexpected errors
     */
    override suspend fun findAllQualifications(): Result<List<Qualification>> = runCatching {
        dataSource.getQualifications().first().map { qualificationDto ->
            qualificationMapper.mapToDomain(qualificationDto)
        }
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch tasks")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping tasks")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    /**
     * Finds a task by its ID.
     *
     * @param taskId The ID of the qualification to find
     * @return [Result] containing the qualification if found, or an error if the operation failed
     * @throws DomainError.TaskError if the qualification is not found
     * @throws DomainError.NetworkError if there's a network-related issue
     * @throws DomainError.MappingError if there's an error mapping the qualification
     */
    override suspend fun findQualificationById(qualificationId: Long): Result<Qualification> = runCatching {
        val qualificationDto =
            dataSource.getQualificationById(qualificationId) ?: throw DomainError.TaskError("Task not found")
        qualificationMapper.mapToDomain(qualificationDto)
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch task")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping task")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun saveQualification(qualification: Qualification): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteQualification(qualificationId: Long): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateQualification(qualification: Qualification): Result<Unit> {
        TODO("Not yet implemented")
    }


}