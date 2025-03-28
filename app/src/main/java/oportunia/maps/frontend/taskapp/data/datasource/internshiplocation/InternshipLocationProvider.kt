package oportunia.maps.frontend.taskapp.data.datasource.internshiplocation

import oportunia.maps.frontend.taskapp.data.datasource.locationcompany.LocationCompanyProvider
import oportunia.maps.frontend.taskapp.domain.model.Internship
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation

/**
 * This class simulates the interaction with InternshipLocation data.
 * @property internshipLocationList A mutable list of InternshipLocation objects.
 * @property findInternshipLocationById Finds an InternshipLocation by its ID.
 * @property findAllInternshipLocations Finds all InternshipLocations.
 * @property addInternshipLocation Adds a new InternshipLocation.
 * @property updateInternshipLocation Updates an existing InternshipLocation.
 * @property deleteInternshipLocation Deletes an InternshipLocation.
 * @constructor Creates an instance of InternshipLocationProvider.
 */

class InternshipLocationProvider {
    companion object {
        private val internshipLocationList = mutableListOf(
            InternshipLocation(
                id = 1,
                location = LocationCompanyProvider.findLocationCompanyById(1) ?: error("Location not found"),
                internship = Internship(
                    id = 1,
                    details = "Software development internship. Work with cutting-edge technologies and contribute to real-world projects."
                )
            ),
            InternshipLocation(
                id = 2,
                location = LocationCompanyProvider.findLocationCompanyById(2) ?: error("Location not found"),
                internship = Internship(
                    id = 2,
                    details = "Marketing and sales internship. Assist with market analysis, customer outreach, and sales strategies."
                )
            ),
            InternshipLocation(
                id = 3,
                location = LocationCompanyProvider.findLocationCompanyById(3) ?: error("Location not found"),
                internship = Internship(
                    id = 3,
                    details = "Financial services internship. Gain insights into the financial sector while working with clients."
                )
            ),
            InternshipLocation(
                id = 3,
                location = LocationCompanyProvider.findLocationCompanyById(3) ?: error("Location not found"),
                internship = Internship(
                    id = 3,
                    details = "Financial services internship. Gain insights into the financial sector while working with clients."
                )
            ),
            InternshipLocation(
                id = 3,
                location = LocationCompanyProvider.findLocationCompanyById(3) ?: error("Location not found"),
                internship = Internship(
                    id = 3,
                    details = "Financial services internship. Gain insights into the financial sector while working with clients."
                )
            ),
            InternshipLocation(
                id = 4,
                location = LocationCompanyProvider.findLocationCompanyById(4) ?: error("Location not found"),
                internship = Internship(
                    id = 4,
                    details = "Logistics internship. Support operations, supply chain management, and customer service processes."
                )
            )
        )

        fun findInternshipLocationById(id: Long): InternshipLocation? {
            return internshipLocationList.find { it.id == id }
        }

        fun findAllInternshipLocations(): List<InternshipLocation> {
            return internshipLocationList
        }

        fun findAllInternshipsByLocationId(locationId: Long): List<Internship> {
            return internshipLocationList.filter { it.location.id == locationId }.map { it.internship }
        }

        fun addInternshipLocation(internshipLocation: InternshipLocation) {
            internshipLocationList.add(internshipLocation)
        }

        fun updateInternshipLocation(internshipLocation: InternshipLocation) {
            val index = internshipLocationList.indexOfFirst { it.id == internshipLocation.id }
            if (index != -1) {
                internshipLocationList[index] = internshipLocation
            }
        }

        fun deleteInternshipLocation(internshipLocation: InternshipLocation) {
            internshipLocationList.remove(internshipLocation)
        }
    }
}