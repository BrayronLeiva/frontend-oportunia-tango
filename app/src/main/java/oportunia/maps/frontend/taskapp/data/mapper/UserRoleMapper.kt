package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.datasource.model.UserRoleDto
import oportunia.maps.frontend.taskapp.domain.model.UserRole

/**
 * Mapper class for converting between UserRole domain entities and UserRoleDto data objects.
 */
class UserRoleMapper(
    private val userMapper: UserMapper,
    private val roleMapper: RoleMapper
) {

    /**
     * Maps a UserRoleDto to a domain UserRole entity.
     * @param dto The data layer user role object to convert.
     * @return Domain UserRole object.
     */
    fun mapToDomain(dto: UserRoleDto): UserRole = UserRole(
        user = userMapper.mapToDomain(dto.user),
        role = roleMapper.mapToDomain(dto.role)
    )

    /**
     * Maps a domain UserRole to a UserRoleDto.
     * @param domain The domain layer user role object to convert.
     * @return UserRoleDto object for data layer.
     */
    fun mapToDto(domain: UserRole): UserRoleDto = UserRoleDto(
        user = userMapper.mapToDto(domain.user),
        role = roleMapper.mapToDto(domain.role)
    )
}