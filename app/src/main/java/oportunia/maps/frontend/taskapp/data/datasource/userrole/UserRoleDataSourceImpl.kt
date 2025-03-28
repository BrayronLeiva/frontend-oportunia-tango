package oportunia.maps.frontend.taskapp.data.datasource.userrole

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import oportunia.maps.frontend.taskapp.data.datasource.model.UserRoleDto
import oportunia.maps.frontend.taskapp.data.mapper.UserRoleMapper

/**
 * UserRoleDataSource implementation.
 * @param userRoleMapper The mapper for converting between data and domain layer UserRole objects.
 */
class UserRoleDataSourceImpl(
    private val userRoleMapper: UserRoleMapper
) : UserRoleDataSource {

    override suspend fun getUserRoles(): Flow<List<UserRoleDto>> = flow {
        val userRoles = UserRoleProvider.findAllUserRoles()
        emit(userRoles.map { userRoleMapper.mapToDto(it) })
    }

    override suspend fun getUserRoleById(userId: Long): UserRoleDto? =
        UserRoleProvider.findUserRoleByUserId(userId)?.let { userRoleMapper.mapToDto(it) }

    override suspend fun getUsersByRole(role: String): Flow<List<UserRoleDto>> {
        UserRoleProvider.findUsersByRole(role)?.let { users ->
            return flow { emit(users.map { userRoleMapper.mapToDto(it) }) }
        }
        return flow { emit(emptyList()) }
    }

    override suspend fun getUserRoleByEmail(email: String): Flow<UserRoleDto?> = flow {
        val userRole = UserRoleProvider.findUserRoleByEmail(email)
        emit(userRole?.let { userRoleMapper.mapToDto(it) })
    }

    override suspend fun insertUserRole(userRoleDto: UserRoleDto) {
        val userRole = userRoleMapper.mapToDomain(userRoleDto)
        UserRoleProvider.addUserRole(userRole)
    }

    override suspend fun updateUserRole(userRoleDto: UserRoleDto) {
        val userRole = userRoleMapper.mapToDomain(userRoleDto)
        UserRoleProvider.updateUserRole(userRole)
    }

    override suspend fun deleteUserRole(userRoleDto: UserRoleDto) {
        val userRole = userRoleMapper.mapToDomain(userRoleDto)
        UserRoleProvider.deleteUserRole(userRole)
    }
}