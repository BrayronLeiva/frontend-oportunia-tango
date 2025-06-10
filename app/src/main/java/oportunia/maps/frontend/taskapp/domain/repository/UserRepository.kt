package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.data.remote.dto.UserCreateDto
import oportunia.maps.frontend.taskapp.domain.model.User

interface UserRepository {
    suspend fun findAllUsers(): Result<List<User>>
    //suspend fun findUserById(id: Long): Result<User>
    suspend fun saveUser(user: User): Result<Unit>
    suspend fun saveUser(user: UserCreateDto): Result<User>
    //suspend fun deleteUser(id: Long): Result<Unit>
    //suspend fun updateUser(user: User): Result<Unit>
}