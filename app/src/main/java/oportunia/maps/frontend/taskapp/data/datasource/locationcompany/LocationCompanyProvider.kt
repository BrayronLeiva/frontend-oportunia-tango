package oportunia.maps.frontend.taskapp.data.datasource.locationcompany

import com.google.android.gms.maps.model.LatLng
import oportunia.maps.frontend.taskapp.domain.model.Company
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import oportunia.maps.frontend.taskapp.domain.model.User
import oportunia.maps.frontend.taskapp.data.datasource.model.enumClasses.InternshipType

/**
 * This class simulates the interaction with LocationCompany data.
 */
class LocationCompanyProvider {
    companion object {
        private val locationCompanyList = mutableListOf(
            LocationCompany(
                id = 1,
                email = "location1@example.com",
                location = LatLng(9.9333, -84.0833),
                contact = 12345678,
                company = Company(
                    id = 1,
                    name = "Company One",
                    description = "A leading tech company.",
                    history = "Founded in 2001.",
                    mision = "To innovate and lead.",
                    vision = "To be the world's tech leader.",
                    corporateCultur = "Inclusive and innovative.",
                    contact = 12345678,
                    rating = 4.5,
                    internshipType = InternshipType.REM,
                    user = User(id = 1, email = "user1@example.com", password = "password123")
                )
            ),
            LocationCompany(
                id = 2,
                email = "location2@example.com",
                location = LatLng(10.0167, -84.2100),
                contact = 98765432,
                company = Company(
                    id = 2,
                    name = "Company Two",
                    description = "An international retail brand.",
                    history = "Founded in 1995.",
                    mision = "To offer quality products at affordable prices.",
                    vision = "To be a global leader in retail.",
                    corporateCultur = "Customer-centric and efficient.",
                    contact = 98765432,
                    rating = 4.0,
                    internshipType = InternshipType.INP,
                    user = User(id = 2, email = "user2@example.com", password = "password456")
                )
            ),
            LocationCompany(
                id = 3,
                email = "location3@example.com",
                location = LatLng(9.9833, -84.1167),
                contact = 11223344,
                company = Company(
                    id = 3,
                    name = "Company Three",
                    description = "A global financial services firm.",
                    history = "Founded in 1950.",
                    mision = "To provide comprehensive financial solutions.",
                    vision = "To be the most trusted financial partner.",
                    corporateCultur = "Integrity and innovation.",
                    contact = 11223344,
                    rating = 4.7,
                    internshipType = InternshipType.MIX,
                    user = User(id = 3, email = "user3@example.com", password = "password789")
                )
            ),
            LocationCompany(
                id = 4,
                email = "location4@example.com",
                location = LatLng(9.8667, -83.9167),
                contact = 99887766,
                company = Company(
                    id = 4,
                    name = "Company Four",
                    description = "A well-established logistics company.",
                    history = "Founded in 1980.",
                    mision = "To streamline global logistics.",
                    vision = "To lead in global logistics innovation.",
                    corporateCultur = "Collaborative and efficient.",
                    contact = 99887766,
                    rating = 4.3,
                    internshipType = InternshipType.REM,
                    user = User(id = 4, email = "user4@example.com", password = "password101")
                )
            )
        )

        /**
         * Finds a location company by its ID.
         * @param id The ID of the location company to find.
         * @return The location company with the given ID, or null if not found.
         */
        fun findLocationCompanyById(id: Long): LocationCompany? {
            return locationCompanyList.find { it.id == id }
        }

        /**
         * Finds all location companies.
         * @return A list of all location companies.
         */
        fun findAllLocationCompanies(): List<LocationCompany> {
            return locationCompanyList
        }

        /**
         * Adds a new location company.
         * @param locationCompany The location company to add.
         */
        fun addLocationCompany(locationCompany: LocationCompany){
            locationCompanyList.add(locationCompany)
        }

        /**
         * Updates an existing location company.
         * @param locationCompany The updated location company.
         */
        fun updateLocationCompany(locationCompany: LocationCompany){
            val index = locationCompanyList.indexOfFirst { it.id == locationCompany.id }
            if (index != -1) {
                locationCompanyList[index] = locationCompany
            }
        }

        /**
         * Deletes a location company.
         * @param locationCompany The location company to delete.
         */
        fun deleteLocationCompany(locationCompany: LocationCompany){
            locationCompanyList.remove(locationCompany)
        }
    }
}