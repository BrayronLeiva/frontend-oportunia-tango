package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyRequestDto
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.InternshipType
import oportunia.maps.frontend.taskapp.domain.model.Company
import javax.inject.Inject

/**
 * Mapper class for converting between Company domain entities and CompanyDto data objects
 */
class CompanyMapper @Inject constructor(
    private val userMapper: UserMapper
) {

    /**
     * Maps a CompanyDto to a domain Company entity
     * @param dto The data layer company object to convert
     * @return Domain Company object
     */
    fun mapToDomain(dto: CompanyDto): Company = Company(
        id = dto.idCompany,
        name = dto.nameCompany,
        description = dto.description,
        history = dto.history,
        mision = dto.mision,
        vision = dto.vision,
        corporateCultur = dto.corporateCultur,
        contact = dto.contactCompany,
        rating = dto.ratingCompany,
        internshipType = InternshipType.valueOf(dto.internshipType),
        imageProfile = dto.imageProfile,
        user = userMapper.mapToDomain(dto.user)
    )

    /**
     * Maps a domain Company to a CompanyDto
     * @param domain The domain layer company object to convert
     * @return CompanyDto object for data layer
     */
    fun mapToDto(domain: Company): CompanyDto =
        CompanyDto(
            idCompany = domain.id!!,
            nameCompany = domain.name,
            description = domain.description,
            history = domain.history,
            mision = domain.mision,
            vision = domain.vision,
            corporateCultur = domain.corporateCultur,
            contactCompany = domain.contact,
            ratingCompany = domain.rating,
            internshipType = domain.internshipType.name,
            imageProfile = domain.imageProfile,
            user = userMapper.mapToDto(domain.user)
        )

    fun mapToRequestDto(domain: Company): CompanyRequestDto =
        CompanyRequestDto(
            nameCompany = domain.name,
            description = domain.description,
            history = domain.history,
            mision = domain.mision,
            vision = domain.vision,
            ratingCompany = domain.rating,
            corporateCultur = domain.corporateCultur,
            contactCompany = domain.contact,
            internshipType = domain.internshipType.name,
            imageProfile = domain.imageProfile,
            userId = domain.user.id
        )

    /*
    fun toDomainFromFlat(flat: LocationCompanyFlatDto): Company {
        return Company(
            id = flat.idCompany,
            name = flat.name,
            description = flat.description,
            history = flat.history,
            mision = flat.mision,
            vision = flat.vision,
            corporateCultur = flat.corporateCultur,
            contact = flat.contactCompany,
            rating = flat.rating,
            internshipType = InternshipType.valueOf(flat.internshipType),
            //user = userMapper.mapToDomain(UserData(flat.idUser, flat.emailUser))
        )
    }

     */

}