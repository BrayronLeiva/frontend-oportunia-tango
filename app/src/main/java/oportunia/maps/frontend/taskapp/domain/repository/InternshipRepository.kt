package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.domain.model.Internship
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation

interface InternshipRepository {
    suspend fun findAllInternships(): Result<List<Internship>>
    suspend fun findInternshipById(id: Long): Result<Internship>
    suspend fun saveInternship(internship: Internship): Result<Internship>
    suspend fun deleteInternship(id: Long): Result<Unit>
    suspend fun updateInternship(internship: Internship): Result<Unit>
    suspend fun findInternshipsByLocationId(locationId: Long): Result<List<Internship>>
}