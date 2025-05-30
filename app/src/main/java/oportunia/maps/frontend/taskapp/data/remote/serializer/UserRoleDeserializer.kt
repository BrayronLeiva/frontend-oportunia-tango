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
        val userId = jsonObject.get("id").asLong
        val firstName = jsonObject.get("firstName").asString
        val lastName = jsonObject.get("lastName").asString
        val emailUser = jsonObject.get("email").asString
        val enable = jsonObject.get("enable").asBoolean
        val tokenExpired = jsonObject.get("tokenExpired").asBoolean
        val createDate = jsonObject.get("createDate").asString
        //val roleList = jsonObject.get("roleList").as

        // Create UserDto and CompanyDto
        val userDto = UserDto(userId, emailUser, firstName, lastName, enable, tokenExpired, createDate)

        val rolId = jsonObject.get("rol_id").asLong
        val roleName = jsonObject.get("rol_name").asString

        val roleDto = RoleDto(rolId,roleName)

        // Return the StudentDto object
        return UserRoleDto(userDto,roleDto)
    }
}