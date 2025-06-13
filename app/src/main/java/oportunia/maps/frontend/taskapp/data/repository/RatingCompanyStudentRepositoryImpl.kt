package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.mapper.RatingCompanyStudentMapper
import oportunia.maps.frontend.taskapp.data.remote.RatingCompanyStudentRemoteDataSource
import oportunia.maps.frontend.taskapp.domain.model.RatingCompanyStudent
import oportunia.maps.frontend.taskapp.domain.repository.RatingCompanyStudentRepository
import java.net.UnknownHostException
import javax.inject.Inject

class RatingCompanyStudentRepositoryImpl @Inject constructor(
    private val remoteDataSource: RatingCompanyStudentRemoteDataSource,
    private val mapper: RatingCompanyStudentMapper
) : RatingCompanyStudentRepository {

    override suspend fun findAllRatingCompanyStudents(): Result<List<RatingCompanyStudent>> {
        return try {
            remoteDataSource.getAll().map { dtos ->
                dtos.map { mapper.mapToDomain(it) }
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Please check your connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching student ratings: ${e.message}"))
        }
    }

    override suspend fun findRatingCompanyStudentById(id: Long): Result<RatingCompanyStudent> {
        return try {
            remoteDataSource.getById(id).map { dto ->
                mapper.mapToDomain(dto)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching rating with id $id: ${e.message}"))
        }
    }

    override suspend fun saveRatingCompanyStudent(ratingCompanyStudent: RatingCompanyStudent): Result<Unit> {
        return try {
            remoteDataSource.create(mapper.mapToRequestDto(ratingCompanyStudent)).map {
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error saving rating: ${e.message}"))
        }
    }

    override suspend fun updateRatingCompanyStudent(ratingCompanyStudent: RatingCompanyStudent): Result<Unit> {
        return try {
            remoteDataSource.update(ratingCompanyStudent.id!!, mapper.mapToDto(ratingCompanyStudent)).map {
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error updating rating: ${e.message}"))
        }
    }

    override suspend fun deleteRatingCompanyStudent(id: Long): Result<Unit> {
        return try {
            remoteDataSource.delete(id)
        } catch (e: Exception) {
            Result.failure(Exception("Error deleting rating with id $id: ${e.message}"))
        }
    }
}