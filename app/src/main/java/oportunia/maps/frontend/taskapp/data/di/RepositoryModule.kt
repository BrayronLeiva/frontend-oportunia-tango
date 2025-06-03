package oportunia.maps.frontend.taskapp.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import oportunia.maps.frontend.taskapp.data.repository.InternshipLocationRepositoryImpl
import oportunia.maps.frontend.taskapp.data.repository.InternshipRepositoryImpl
import oportunia.maps.frontend.taskapp.data.repository.LocationCompanyRepositoryImpl
import oportunia.maps.frontend.taskapp.data.repository.QualificationRepositoryImpl
import oportunia.maps.frontend.taskapp.data.repository.StudentRepositoryImpl
import oportunia.maps.frontend.taskapp.data.repository.UserRepositoryImpl
import oportunia.maps.frontend.taskapp.data.repository.UserRoleRepositoryImpl
import oportunia.maps.frontend.taskapp.domain.repository.InternshipLocationRepository
import oportunia.maps.frontend.taskapp.domain.repository.InternshipRepository
import oportunia.maps.frontend.taskapp.domain.repository.LocationCompanyRepository
import oportunia.maps.frontend.taskapp.domain.repository.QualificationRepository
import oportunia.maps.frontend.taskapp.domain.repository.StudentRepository
import oportunia.maps.frontend.taskapp.domain.repository.UserRepository
import oportunia.maps.frontend.taskapp.domain.repository.UserRoleRepository
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides repository dependencies for the application.
 *
 * This module uses [Binds] to map implementation classes to their interfaces,
 * allowing Hilt to inject the correct implementation when an interface type is requested.
 * All bindings are scoped to [SingletonComponent] to ensure a single instance is shared
 * throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds the concrete implementation [LocationCompanyRepositoryImpl] to the [LocationCompanyRepository] interface.
     *
     * @param locationCompanyRepositoryImpl The implementation instance to be provided when [LocationCompanyRepository] is requested
     * @return The bound [LocationCompanyRepository] interface
     */
    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        locationCompanyRepositoryImpl: LocationCompanyRepositoryImpl
    ): LocationCompanyRepository

    /**
     * Binds the concrete implementation [QualificationRepositoryImpl] to the [QualificationRepository] interface.
     *
     * @param QualificationRepositoryImpl The implementation instance to be provided when [QualificationRepository] is requested
     * @return The bound [QualificationRepository] interface
     */
    @Binds
    @Singleton
    abstract fun bindQualificationRepository(
        qualificationRepositoryImpl: QualificationRepositoryImpl
    ): QualificationRepository

    /**
     * Binds the concrete implementation [studentRepositoryImpl] to the [StudentRepository] interface.
     *
     * @param studentRepositoryImpl The implementation instance to be provided when [StudentRepository] is requested
     * @return The bound [StudentRepository] interface
     */
    @Binds
    @Singleton
    abstract fun bindStudentRepository(
        studentRepositoryImpl: StudentRepositoryImpl
    ): StudentRepository


    /**
     * Binds the concrete implementation [internshipLocationRepositoryImpl] to the [internshipLocationRepository] interface.
     *
     * @param userRoleRepositoryImpl The implementation instance to be provided when [UserRoleRepository] is requested
     * @return The bound [UserRoleRepository] interface
     */


    @Binds
    @Singleton
    abstract fun bindInternshipLocationRepository(
        internshipLocationRepositoryImpl: InternshipLocationRepositoryImpl
    ): InternshipLocationRepository


    /**
     * Binds the concrete implementation [userRoleRepositoryImpl] to the [UserRoleRepository] interface.
     *
     * @param userRoleRepositoryImpl The implementation instance to be provided when [UserRoleRepository] is requested
     * @return The bound [UserRoleRepository] interface
     */


    @Binds
    @Singleton
    abstract fun binduserRoleRepository(
        userRoleRepositoryImpl: UserRoleRepositoryImpl
    ): UserRoleRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository



    /**
     * Binds the concrete implementation [internshipLocationRepositoryImpl] to the [internshipLocationRepository] interface.
     *
     * @param userRoleRepositoryImpl The implementation instance to be provided when [UserRoleRepository] is requested
     * @return The bound [UserRoleRepository] interface
     */


    @Binds
    @Singleton
    abstract fun bindInternshipRepository(
        internshipRepositoryImpl: InternshipRepositoryImpl
    ): InternshipRepository
}