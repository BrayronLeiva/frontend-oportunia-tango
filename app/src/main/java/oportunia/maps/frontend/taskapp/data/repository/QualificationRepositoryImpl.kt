package oportunia.maps.frontend.taskapp.data.repository


import oportunia.maps.frontend.taskapp.data.mapper.QualificationMapper
import oportunia.maps.frontend.taskapp.data.remote.QualificationRemoteDataSource
import oportunia.maps.frontend.taskapp.domain.model.Qualification
import oportunia.maps.frontend.taskapp.domain.repository.QualificationRepository
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Implementation of [TaskRepository] that handles task data operations.
 * Provides error handling and mapping between data and domain layers.
 *
 * @property dataSource The data source for task operations
 * @property taskMapper The mapper for converting between domain and data layer task objects
 */
class QualificationRepositoryImpl @Inject constructor(
    private val dataSource: QualificationRemoteDataSource,
    private val qualificationMapper: QualificationMapper
) : QualificationRepository {

    /**
     * Retrieves all tasks from the data source.
     *
     * @return [Result] containing a list of qualifications if successful, or an error if the operation failed
     */


    override suspend fun findAllQualifications(): Result<List<Qualification>> {
        return try {
            dataSource.getAll().map { dtos ->
                dtos.map { qualificationMapper.mapToDomain(it) }
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


    /**
     * Retrieves a location company by its ID.
     */
    override suspend fun findQualificationById(id: Long): Result<Qualification> {
        return dataSource.getById(id).map {
            qualificationMapper.mapToDomain(it)
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