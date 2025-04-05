package oportunia.maps.frontend.taskapp.domain.model

import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser

/**
 * This class represents a Role.
 * @property id The unique identifier of the role.
 * @property name The name of the role.
 */

data class Role(
    val id: Long,
    val name: TypeUser
)
