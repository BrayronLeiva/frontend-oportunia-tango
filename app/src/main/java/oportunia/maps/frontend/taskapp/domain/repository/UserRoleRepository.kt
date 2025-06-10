package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.data.remote.dto.UserRoleCreateDto
import oportunia.maps.frontend.taskapp.domain.model.User
import oportunia.maps.frontend.taskapp.domain.model.UserRole

interface UserRoleRepository {
    suspend fun findAllUserRoles(): Result<List<UserRole>>
    //suspend fun findUserRoleById(id: Long): Result<UserRole>
    //suspend fun findUsersByRole(role: UserRole): Result<List<User>>
    //suspend fun getUserRoleByEmail(email: String): Result<UserRole?>
    suspend fun saveUserRole(userRole: UserRoleCreateDto): Result<UserRole>
    //suspend fun deleteUserRole(id: Long): Result<Unit>
    //suspend fun updateUserRole(userRole: UserRole): Result<Unit>

}