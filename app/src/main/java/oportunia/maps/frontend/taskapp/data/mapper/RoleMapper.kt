package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.remote.dto.RoleDto
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.domain.model.Role

/**
 * Mapper class for converting between Role domain entities and RoleDto data objects.
 */
class RoleMapper {

    /**
     * Maps a RoleDto to a domain Role entity.
     * @param dto The data layer role object to convert.
     * @return Domain Role object.
     */
    fun mapToDomain(dto: RoleDto): Role = Role(
        id = dto.id,
        name = oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser.valueOf(dto.name)
    )

    /**
     * Maps a domain Role to a RoleDto.
     * @param domain The domain layer role object to convert.
     * @return RoleDto object for data layer.
     */
    fun mapToDto(domain: Role): RoleDto =
        RoleDto(
            id = domain.id,
            name = (domain.name).name
        )
}