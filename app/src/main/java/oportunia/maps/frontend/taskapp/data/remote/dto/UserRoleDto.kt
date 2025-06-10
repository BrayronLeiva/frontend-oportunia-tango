package oportunia.maps.frontend.taskapp.data.remote.dto

data class UserRoleDto(
    val user: UserDto,
    val role: RoleDto
)

data class UserRoleCreateDto(
    val roleId: Long,
    val userId: Long
)
