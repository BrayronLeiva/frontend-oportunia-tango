package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.mapper.UserMapper
import oportunia.maps.frontend.taskapp.domain.model.User
import oportunia.maps.frontend.taskapp.domain.repository.UserRepository
import oportunia.maps.frontend.taskapp.data.remote.UserRemoteDataSource
import java.net.UnknownHostException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserRemoteDataSource,
    private val userMapper: UserMapper
) : UserRepository {

    override suspend fun findAllUsers(): Result<List<User>> {
        return try {
            val dtosRes = dataSource.getAll().getOrThrow()
            println("DEBUG: Received $dtosRes")
            dataSource.getAll().map { dtos ->
                dtos.map { userMapper.mapToDomain(it) }
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace() // log full error
            Result.failure(Exception("Network error: Please check your connection."))
        } catch (e: Exception) {
            e.printStackTrace() // log full error
            Result.failure(Exception("Error fetching users: ${e.message}"))
        }
    }

    override suspend fun saveUser(user: User): Result<Unit> {
        return dataSource.create(userMapper.mapToDto(user)).map {
            userMapper.mapToDomain(it)
        }
    }
/*
    override suspend fun findUserById(id: Long): Result<User> = runCatching {
        val userDto = dataSource.getUserById(id) ?: throw DomainError.TaskError("User not found")
        userMapper.mapToDomain(userDto)
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch user")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping user")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun deleteUser(id: Long): Result<Unit> = runCatching {
        dataSource.deleteUser(user)
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to delete user")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun updateUser(user: User): Result<Unit> = runCatching {
        dataSource.updateUser(userMapper.mapToDto(user))
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to update user")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping user")
            else -> throw DomainError.TaskError("Failed to update user: ${throwable.message}")
        }
    }*/
}
