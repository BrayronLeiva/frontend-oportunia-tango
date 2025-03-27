package oportunia.maps.frontend.taskapp.data.datasource.userrole

import kotlinx.coroutines.flow.Flow
import oportunia.maps.frontend.taskapp.data.datasource.model.UserRoleDto

interface UserRoleDataSource {
    suspend fun getUserRoles(): Flow<List<UserRoleDto>>
    suspend fun getUserRoleById(userId: Long): UserRoleDto?
    suspend fun getUsersByRole(role: String): Flow<List<UserRoleDto>>
    suspend fun insertUserRole(userRoleDto: UserRoleDto)
    suspend fun updateUserRole(userRoleDto: UserRoleDto)
    suspend fun deleteUserRole(userRoleDto: UserRoleDto)

}