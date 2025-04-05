package oportunia.maps.frontend.taskapp.data.remote.dto

/**
 * This class represents the Dto of a User.
 * @property id The unique identifier of the user
 * @property email The email of the user
 * @property password The password of the user
 */

data class UserDto(
    val id: Long,
    val email: String,
    val password: String
)
