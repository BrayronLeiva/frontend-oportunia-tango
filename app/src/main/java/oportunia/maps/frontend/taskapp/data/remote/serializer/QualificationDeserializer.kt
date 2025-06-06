package oportunia.maps.frontend.taskapp.data.remote.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RoleDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentDto
import oportunia.maps.frontend.taskapp.data.remote.dto.UserDto
import oportunia.maps.frontend.taskapp.domain.model.Student
import java.lang.reflect.Type


/**
 * Custom JSON deserializer for [QualificationDto] objects.
 *
 * Handles nested LatLng and Qualification deserialization.
 */
class QualificationDeserializer : JsonDeserializer<QualificationDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): QualificationDto {
        val jsonObject = json.asJsonObject

        // Deserialize LocationCompanyDto fields
        val id = jsonObject.get("id").asLong
        val name = jsonObject.get("name").asString
        val area = jsonObject.get("area").asString


        // Manually deserialize the flattened company fields
        val studentId = jsonObject.get("idStudent").asLong
        val studentName = jsonObject.get("studentName").asString
        val identification = jsonObject.get("identification").asString
        val personalInfo = jsonObject.get("personalInfo").asString
        val experience = jsonObject.get("experience").asString
        val rating = jsonObject.get("rating").asDouble


        // Deserialize user information
        val userId = jsonObject.get("id").asLong
        val firstName = jsonObject.get("firstName").asString
        val lastName = jsonObject.get("lastName").asString
        val emailUser = jsonObject.get("email").asString
        val enable = jsonObject.get("enable").asBoolean
        val tokenExpired = jsonObject.get("tokenExpired").asBoolean
        val createDate = jsonObject.get("createDate").asString
        val roleListJson = jsonObject.getAsJsonArray("roleList")
        val roleList: List<RoleDto> = roleListJson.map { element ->
            val roleObject = element.asJsonObject
            val idrole = roleObject.get("id").asLong
            val namerole = roleObject.get("name").asString

            RoleDto(id = idrole, name = namerole)
        }

        // Create UserDto and CompanyDto
        val userDto = UserDto(userId, emailUser, firstName, lastName, enable, tokenExpired, createDate, roleList)

        val studentDto = StudentDto(
            studentId, studentName, identification, personalInfo,
            experience, rating, userDto
        )

        // Return the LocationCompanyDto object
        return QualificationDto(id, name, area, studentDto)
    }
}