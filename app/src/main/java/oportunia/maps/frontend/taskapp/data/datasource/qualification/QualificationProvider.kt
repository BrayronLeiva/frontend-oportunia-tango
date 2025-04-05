package oportunia.maps.frontend.taskapp.data.datasource.qualification

import oportunia.maps.frontend.taskapp.domain.model.Qualification

//listOf("Kotlin", "Java", "Android", "Flutter", "Dart", "Python", "Laravel", "Angular", "React Native")
class QualificationProvider {
    companion object {
        private val qualificationList = listOf(
            Qualification(
                id = 1,
                name = "Kotlin",
                area = "Software",
                student = null
            ),
            Qualification(
                id = 2,
                name = "Java",
                area = "Software",
                student = null
            ),
            Qualification(
                id = 3,
                name = "Android",
                area = "Software",
                student = null
            ),
            Qualification(
                id = 4,
                name = "Flutter",
                area = "Software",
                student = null
            ),
            Qualification(
                id = 5,
                name = "Dart",
                area = "Software",
                student = null
            ),
            Qualification(
                id = 6,
                name = "Python",
                area = "Software",
                student = null
            ),
            Qualification(
                id = 7,
                name = "Laravel",
                area = "Software",
                student = null
            ),
            Qualification(
                id = 8,
                name = "Angular",
                area = "Software",
                student = null
            ),
            Qualification(
                id = 9,
                name = "React Native",
                area = "Software",
                student = null
            ),
            Qualification(
                id = 10,
                name = "English",
                area = "Languages",
                student = null
            )
        )

        /**
         * Finds a task by its ID.
         * @param id The ID of the task to find.
         * @return The task with the given ID, or null if not found.
         */
        fun findQualificationById(id: Long): Qualification? {
            return qualificationList.find { it.id == id }
        }

        /**
         * Finds all tasks.
         * @return A list of all tasks.
         */
        fun findAllQualifications(): List<Qualification> {
            return qualificationList
        }
    }
}