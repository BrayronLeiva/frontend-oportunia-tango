package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.remote.dto.StudentDto
import oportunia.maps.frontend.taskapp.domain.model.Student
import javax.inject.Inject

/**
 * Mapper class for converting between Student domain entities and StudentDto data objects
 */
class StudentMapper @Inject constructor(private val userMapper: UserMapper) {

    /**
     * Maps a StudentDto to a domain Student entity
     * @param dto The data layer student object to convert
     * @return Domain Student object
     */
    fun mapToDomain(dto: StudentDto): Student = Student(
        id = dto.id,
        name = dto.name,
        identification = dto.identification,
        personalInfo = dto.personalInfo,
        experience = dto.experience,
        rating = dto.rating,
        homeLatitude = dto.homeLatitude,
        homeLongitude = dto.homeLongitude,
        imageProfile = dto.imageProfile,
        user = userMapper.mapToDomain(dto.user)
    )

    /**
     * Maps a domain Student to a StudentDto
     * @param domain The domain layer student object to convert
     * @return StudentDto object for data layer
     */
    fun mapToDto(domain: Student): StudentDto =
        StudentDto(
            id = domain.id,
            name = domain.name,
            identification = domain.identification,
            personalInfo = domain.personalInfo,
            experience = domain.experience,
            rating = domain.rating,
            homeLatitude = domain.homeLatitude,
            homeLongitude = domain.homeLongitude,
            imageProfile = domain.imageProfile,
            user = userMapper.mapToDto(domain.user)
        )
}