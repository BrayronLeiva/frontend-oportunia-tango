package oportunia.maps.frontend.taskapp.data.di

import oportunia.maps.frontend.taskapp.data.remote.serializer.LocationCompanyDeserializer
import oportunia.maps.frontend.taskapp.data.remote.api.LocationCompanyService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import oportunia.maps.frontend.taskapp.data.remote.api.AuthService
import oportunia.maps.frontend.taskapp.data.remote.api.CompanyService
import oportunia.maps.frontend.taskapp.data.remote.api.InternshipLocationService
import oportunia.maps.frontend.taskapp.data.remote.api.InternshipService
import oportunia.maps.frontend.taskapp.data.remote.api.QualificationService
import oportunia.maps.frontend.taskapp.data.remote.api.RequestService
import oportunia.maps.frontend.taskapp.data.remote.api.StudentService
import oportunia.maps.frontend.taskapp.data.remote.api.UserRoleService
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipDto
import oportunia.maps.frontend.taskapp.data.remote.api.UserService
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentDto
import oportunia.maps.frontend.taskapp.data.remote.dto.UserRoleDto
import oportunia.maps.frontend.taskapp.data.remote.interceptor.AuthInterceptor
import oportunia.maps.frontend.taskapp.data.remote.interceptor.ResponseInterceptor
import oportunia.maps.frontend.taskapp.data.remote.serializer.InternshipDeserializer
import oportunia.maps.frontend.taskapp.data.remote.serializer.InternshipLocationDeserializer
import oportunia.maps.frontend.taskapp.data.remote.serializer.InternshipLocationRecommendedSerializer
import oportunia.maps.frontend.taskapp.data.remote.serializer.QualificationDeserializer
import oportunia.maps.frontend.taskapp.data.remote.serializer.RequestDeserializer
import oportunia.maps.frontend.taskapp.data.remote.serializer.StudentDeserializer
import oportunia.maps.frontend.taskapp.data.remote.serializer.UserRoleDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides network-related dependencies for the application.
 *
 * This module is responsible for providing singleton instances of:
 * - Gson for JSON serialization/deserialization
 * - HTTP client configuration with logging
 * - Retrofit service configuration
 * - API service interfaces
 *
 *
 * @property BASE_URL The base URL for the API endpoints
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://oportunia-maps-34f81e092b2b.herokuapp.com/"
    private const val DATE_FORMAT = "yyyy-MM-dd"

    /**
     * Provides a singleton Gson instance configured with custom type adapters.
     *
     * @return Configured [Gson] instance
     */
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapter(RequestDto::class.java, RequestDeserializer())
        .registerTypeAdapter(InternshipDto::class.java, InternshipDeserializer())
        .registerTypeAdapter(InternshipLocationRecommendedDto::class.java, InternshipLocationRecommendedSerializer())
        .registerTypeAdapter(InternshipLocationDto::class.java, InternshipLocationDeserializer())
        .registerTypeAdapter(LocationCompanyDto::class.java, LocationCompanyDeserializer())
        .registerTypeAdapter(QualificationDto::class.java, QualificationDeserializer())
        .registerTypeAdapter(StudentDto::class.java, StudentDeserializer())
        .registerTypeAdapter(UserRoleDto::class.java, UserRoleDeserializer())
        .setDateFormat(DATE_FORMAT)
        .create()

    /**
     * Provides a logging interceptor for HTTP request/response logging.
     *
     * @return Configured [HttpLoggingInterceptor]
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }

    /**
     * Provides a configured OkHttpClient with interceptors.
     *
     * @param loggingInterceptor For logging HTTP traffic
     * @param responseInterceptor For handling API responses
     * @return Configured [OkHttpClient]
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        responseInterceptor: ResponseInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(responseInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Provides a configured Retrofit instance.
     *
     * @param okHttpClient The HTTP client to use
     * @param gson The Gson instance for JSON conversion
     * @return Configured [Retrofit] instance
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    /**
     * Provides the LocationCompanyService implementation.
     *
     * @param retrofit The Retrofit instance
     * @return Implementation of [LocationCompanyService]
     */
    @Provides
    @Singleton
    fun provideTaskService(retrofit: Retrofit): LocationCompanyService =
        retrofit.create(LocationCompanyService::class.java)


    /**
     * Provides the Qualification implementation.
     *
     * @param retrofit The Retrofit instance
     * @return Implementation of [QualificationService]
     */
    @Provides
    @Singleton
    fun provideQualificationService(retrofit: Retrofit): QualificationService =
        retrofit.create(QualificationService::class.java)

    /**
     * Provides the Student implementation.
     *
     * @param retrofit The Retrofit instance
     * @return Implementation of [StudentService]
     */
    @Provides
    @Singleton
    fun provideStudentService(retrofit: Retrofit): StudentService =
        retrofit.create(StudentService::class.java)

    /**
     * Provides the Student implementation.
     *
     * @param retrofit The Retrofit instance
     * @return Implementation of [UserRoleService]
     */
    @Provides
    @Singleton
    fun provideUserRoleService(retrofit: Retrofit): UserRoleService =
        retrofit.create(UserRoleService::class.java)


    /**
     * Provides the Student implementation.
     *
     * @param retrofit The Retrofit instance
     * @return Implementation of [InternshipLocationService]
     */
    @Provides
    @Singleton
    fun provideInternshipLocationService(retrofit: Retrofit): InternshipLocationService =
        retrofit.create(InternshipLocationService::class.java)



    /**
     * Provides the Student implementation.
     *
     * @param retrofit The Retrofit instance
     * @return Implementation of [InternshipLocationService]
     */
    @Provides
    @Singleton
    fun provideInternshipService(retrofit: Retrofit): InternshipService =
        retrofit.create(InternshipService::class.java)

    /**
     * Provides the Student implementation.
     *
     * @param retrofit The Retrofit instance
     * @return Implementation of [InternshipLocationService]
     */
    @Provides
    @Singleton
    fun provideRequestService(retrofit: Retrofit): RequestService =
        retrofit.create(RequestService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideCompanyService(retrofit: Retrofit): CompanyService {
        return retrofit.create(CompanyService::class.java)
    }
}