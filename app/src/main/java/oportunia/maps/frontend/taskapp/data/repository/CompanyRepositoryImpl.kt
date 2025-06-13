package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.mapper.CompanyMapper
import oportunia.maps.frontend.taskapp.data.remote.CompanyRemoteDataSource
import oportunia.maps.frontend.taskapp.domain.model.Company
import oportunia.maps.frontend.taskapp.domain.repository.CompanyRepository
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Implementation of [CompanyRepository] that handles company data operations.
 *
 * @property remoteDataSource Remote data source for Company
 * @property mapper Mapper to convert between DTO and domain models
 */
class CompanyRepositoryImpl @Inject constructor(
    private val remoteDataSource: CompanyRemoteDataSource,
    private val mapper: CompanyMapper
) : CompanyRepository {

    override suspend fun findAllCompanies(): Result<List<Company>> {
        return try {
            remoteDataSource.getAll().map { dtos ->
                dtos.map { mapper.mapToDomain(it) }
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Please check your connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching companies: ${e.message}"))
        }
    }

    override suspend fun findCompanyById(id: Long): Result<Company> {
        return remoteDataSource.getById(id).map {
            mapper.mapToDomain(it)
        }
    }

    override suspend fun saveCompany(company: Company): Result<Company> {
        return remoteDataSource.create(mapper.mapToRequestDto(company)).map {
            mapper.mapToDomain(it)
        }
    }

    override suspend fun updateCompany(company: Company): Result<Unit> {
        return remoteDataSource.update(company.id!!, mapper.mapToDto(company)).map {
            mapper.mapToDomain(it)
        }
    }

    override suspend fun deleteCompany(id: Long): Result<Unit> {
        return remoteDataSource.delete(id)
    }

    override suspend fun loggedCompany(): Result<Company> {
        return remoteDataSource.loggedCompany().map {
            mapper.mapToDomain(it)
        }
    }
}