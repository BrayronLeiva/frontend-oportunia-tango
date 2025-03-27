package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents a User Role.
 * @property user The  user
 * @property role The role of the user
 */

data class UserRole(
    val user: User,
    val role: Role
)
