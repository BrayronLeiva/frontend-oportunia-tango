package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.datasource.model.UserDto
import oportunia.maps.frontend.taskapp.domain.model.User

/**
 * Mapper class for converting between User domain entities and UserDto data objects
 */
class UserMapper {

    /**
     * Maps a UserDto to a domain User entity
     * @param dto The data layer user object to convert
     * @return Domain User object
     */
    fun mapToDomain(dto: UserDto): User = User(
        id = dto.id,
        email = dto.email,
        password = dto.password
    )

    /**
     * Maps a domain User to a UserDto
     * @param domain The domain layer user object to convert
     * @return UserDto object for data layer
     */
    fun mapToDto(domain: User): UserDto = UserDto(
        id = domain.id,
        email = domain.email,
        password = domain.password
    )
}