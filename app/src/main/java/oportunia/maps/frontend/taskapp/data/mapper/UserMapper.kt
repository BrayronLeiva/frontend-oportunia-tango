package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.remote.dto.UserDto
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.domain.model.Role
import oportunia.maps.frontend.taskapp.domain.model.User
import javax.inject.Inject

/**
 * Mapper class for converting between User domain entities and UserDto data objects
 */
class UserMapper @Inject constructor(
    private val roleMapper: RoleMapper
) {

    /**
     * Maps a UserDto to a domain User entity
     * @param dto The data layer user object to convert
     * @return Domain User object
     */
    fun mapToDomain(dto: UserDto): User = User(
        id = dto.id,
        email = dto.email,
        firstName = dto.firstName,
        lastName = dto.lastName,
        enabled = dto.enable,
        tokenExpired = dto.tokenExpired,
        createDate = dto.createDate,
        roles = dto.roleList.mapNotNull { roleDto ->
            try {
                Role(
                    id = roleDto.id,
                    name = TypeUser.valueOf(roleDto.name)
                )
            } catch (e: Exception) {
                println("Role mapping failed: ${e.message}")
                null
            }
        },
        password = "" // Password not returned from API
    )

    /**
     * Maps a domain User to a UserDto
     * @param domain The domain layer user object to convert
     * @return UserDto object for data layer
     */
    fun mapToDto(domain: User): UserDto =
        UserDto(
            id = domain.id,
            email = domain.email,
            firstName = domain.firstName,
            lastName = domain.lastName,
            enable = true,
            tokenExpired = false,
            roleList = domain.roles.map { roleMapper.mapToDto(it) },
            createDate = domain.createDate
        )
}
