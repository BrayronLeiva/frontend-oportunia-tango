package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.domain.model.Company
import java.io.File

/**
 * This interface represents the CompanyRepository.
 */
interface CompanyRepository {
    suspend fun findAllCompanies(): Result<List<Company>>
    suspend fun findCompanyById(id: Long): Result<Company>
    suspend fun saveCompany(company: Company): Result<Company>
    suspend fun updateCompany(company: Company): Result<Unit>
    suspend fun deleteCompany(id: Long): Result<Unit>
    suspend fun loggedCompany(): Result<Company>
}