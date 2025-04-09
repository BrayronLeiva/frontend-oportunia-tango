package oportunia.maps.frontend.taskapp.data.remote.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RoleDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentDto
import oportunia.maps.frontend.taskapp.data.remote.dto.UserDto
import oportunia.maps.frontend.taskapp.data.remote.dto.UserRoleDto
import oportunia.maps.frontend.taskapp.domain.model.Role
import java.lang.reflect.Type

/**
 * Custom JSON deserializer for [UserRoleDto] objects.
 *
 * Handles nested LatLng and Qualification deserialization.
 */
class UserRoleDeserializer : JsonDeserializer<UserRoleDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): UserRoleDto {
        val jsonObject = json.asJsonObject

        // Deserialize user information
        val userId = jsonObject.get("user_id").asLong
        val userEmail = jsonObject.get("user_email").asString
        val userPassword = jsonObject.get("user_password").asString

        // Create UserDto and CompanyDto
        val userDto = UserDto(userId, userEmail, userPassword)

        val rolId = jsonObject.get("rol_id").asLong
        val roleName = jsonObject.get("rol_name").asString

        val roleDto = RoleDto(rolId,roleName)

        // Return the StudentDto object
        return UserRoleDto(userDto,roleDto)
    }
}