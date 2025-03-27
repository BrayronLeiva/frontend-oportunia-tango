package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.domain.model.User
import oportunia.maps.frontend.taskapp.domain.model.UserRole

interface UserRoleRepository {
    suspend fun findAllUserRoles(): Result<List<UserRole>>
    suspend fun findUserRoleById(id: Long): Result<UserRole>
    suspend fun findUsersByRole(role: UserRole): Result<List<User>>
    suspend fun saveUserRole(userRole: UserRole): Result<Unit>
    suspend fun deleteUserRole(id: Long): Result<Unit>
    suspend fun updateUserRole(userRole: UserRole): Result<Unit>

}