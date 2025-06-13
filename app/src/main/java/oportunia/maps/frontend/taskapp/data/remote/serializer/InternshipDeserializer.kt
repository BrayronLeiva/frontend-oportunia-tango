package oportunia.maps.frontend.taskapp.data.remote.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipDto
import java.lang.reflect.Type


class InternshipDeserializer : JsonDeserializer<InternshipDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): InternshipDto {
        val jsonObject = json.asJsonObject

        val idInternship = jsonObject.get("idInternship").asLong
        val details = jsonObject.get("details").asString

        // Compose final object
        return InternshipDto(idInternship, details)
    }
}