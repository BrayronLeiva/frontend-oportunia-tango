package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.mapper.RequestMapper
import oportunia.maps.frontend.taskapp.data.remote.RequestRemoteDataSource
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestUpdateDto
import oportunia.maps.frontend.taskapp.domain.model.Request
import oportunia.maps.frontend.taskapp.domain.repository.RequestRepository
import java.net.UnknownHostException
import javax.inject.Inject


/**
 * Implementation of [TaskRepository] that handles task data operations.
 * Provides error handling and mapping between data and domain layers.
 *
 * @property dataSource The data source for task operations
 * @property taskMapper The mapper for converting between domain and data layer task objects
 */
class RequestRepositoryImpl @Inject constructor(
    private val dataSource: RequestRemoteDataSource,
    private val requestMapper: RequestMapper
) : RequestRepository {

    /**
     * Retrieves all tasks from the data source.
     *
     * @return [Result] containing a list of qualifications if successful, or an error if the operation failed
     */


    override suspend fun findAllRequests(): Result<List<Request>> {
        return try {
            dataSource.getAll().map { dtos ->
                dtos.map { requestMapper.mapToDomain(it) }
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
     * @param taskId The ID of the qualification to find
     * @return [Result] containing the qualification if found, or an error if the operation failed

     */


    override suspend fun findRequestById(id: Long): Result<Request> {
        return dataSource.getById(id).map {
            requestMapper.mapToDomain(it)
        }
    }

    override suspend fun saveRequest(request: RequestCreateDto): Result<Request> {
        return dataSource.create(request).map {
            requestMapper.mapToDomain(it)
        }
    }

    override suspend fun deleteRequest(requestId: Long): Result<Unit> {
        return dataSource.delete(requestId)
    }

    override suspend fun updateRequest(request: RequestUpdateDto): Result<Request> {
        return dataSource.update(request).map {
            requestMapper.mapToDomain(it)
        }
    }


}