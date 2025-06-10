package oportunia.maps.frontend.taskapp.data.remote.serializer

import android.util.Log
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

        val userJson = jsonObject.getAsJsonObject("user")
        val userId = userJson.get("id").asLong
        val firstName = userJson.get("firstName").asString
        val lastName = userJson.get("lastName").asString
        val emailUser = userJson.get("email").asString
        val enable = userJson.get("enabled").asBoolean
        val tokenExpired = try {
            userJson.get("tokenExpired")?.asBoolean ?: false
        } catch (e: Exception) {
            Log.e("StudentDeserializer", "Error al obtener 'tokenExpired': ${e.message}")
            false
        }
        val createDate = userJson.get("createDate").asString

        val roleListJson = userJson.getAsJsonArray("roleList")
        val roleList: List<RoleDto> = roleListJson.map { element ->
            val roleObject = element.asJsonObject
            val idrole = roleObject.get("id").asLong
            val namerole = roleObject.get("name").asString
            RoleDto(id = idrole, name = namerole)
        }

        val userDto = UserDto(
            id = userId,
            email = emailUser,
            firstName = firstName,
            lastName = lastName,
            enable = enable,
            tokenExpired = tokenExpired,
            createDate = createDate,
            roleList = roleList
        )

        // Obtener el objeto anidado "role"
        val roleJson = jsonObject.getAsJsonObject("role")
        val roleId = roleJson.get("id").asLong
        val roleName = roleJson.get("name").asString

        val roleDto = RoleDto(id = roleId, name = roleName)

        return UserRoleDto(user = userDto, role = roleDto)
    }
}
