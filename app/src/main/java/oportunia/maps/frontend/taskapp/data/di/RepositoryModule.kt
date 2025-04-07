package oportunia.maps.frontend.taskapp.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import oportunia.maps.frontend.taskapp.data.repository.LocationCompanyRepositoryImpl
import oportunia.maps.frontend.taskapp.data.repository.QualificationRepositoryImpl
import oportunia.maps.frontend.taskapp.domain.repository.LocationCompanyRepository
import oportunia.maps.frontend.taskapp.domain.repository.QualificationRepository
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
     * Binds the concrete implementation [LocationCompanyRepositoryImpl] to the [LocationCompanyRepository] interface.
     *
     * @param QualificationRepositoryImpl The implementation instance to be provided when [QualificationRepository] is requested
     * @return The bound [QualificationRepository] interface
     */
    @Binds
    @Singleton
    abstract fun bindQualificationRepository(
        qualificationRepositoryImpl: QualificationRepositoryImpl
    ): QualificationRepository
}