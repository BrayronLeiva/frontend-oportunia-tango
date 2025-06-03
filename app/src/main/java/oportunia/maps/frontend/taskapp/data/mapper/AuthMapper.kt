package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.remote.dto.AuthRequestDto
import oportunia.maps.frontend.taskapp.data.remote.dto.AuthResponseDto
import oportunia.maps.frontend.taskapp.domain.model.AuthResult
import oportunia.maps.frontend.taskapp.domain.model.Credentials

/**
 * Mapper for converting between Auth DTOs and domain models
 */
object AuthMapper {
    /**
     * Converts credentials to DTO
     */
    fun credentialsToDto(credentials: Credentials): AuthRequestDto {
        return AuthRequestDto(
            username = credentials.username,
            password = credentials.password
        )
    }

    /**
     * Converts authentication response DTO to domain model
     */
    fun dtoToAuthResult(dto: AuthResponseDto): AuthResult {
        return AuthResult(
            token = dto.token,
            userId = dto.userId
        )
    }
}