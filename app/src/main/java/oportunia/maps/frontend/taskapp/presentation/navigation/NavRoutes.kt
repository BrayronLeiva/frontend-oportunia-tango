package oportunia.maps.frontend.taskapp.presentation.navigation

/**
 * Contains all navigation route constants for the application.
 *
 * Using a sealed class with objects ensures type safety and prevents errors from misspelled route strings.
 */
sealed class NavRoutes {
    data object TaskList : NavRoutes() {
        const val ROUTE = "taskList"
    }

    data object Home : NavRoutes() {
        const val ROUTE = "home"
    }

    data object StudentMap : NavRoutes() {
        const val ROUTE = "studentMap"
    }

    data object CompanyMap : NavRoutes() {
        const val ROUTE = "companyMap"
    }

    data object LocationCompanyDetail : NavRoutes() {
        const val ROUTE = "locationCompanyDetail/{locationCompanyId}"
        const val ARG_LOCATION_COMPANY_ID = "locationCompanyId" // Changed to uppercase with underscores

        fun createRoute(locationCompanyId: Long) = "locationCompanyDetail/$locationCompanyId"
    }

    data object InternshipList : NavRoutes() {
        const val ROUTE = "internships/{locationCompanyId}"
        const val ARG_LOCATION_COMPANY_ID = "locationCompanyId" // Changed to uppercase with underscores

        fun createRoute(locationCompanyId: Long) = "internships/$locationCompanyId"
    }

    data object TaskDetail : NavRoutes() {
        const val ROUTE = "taskDetail/{taskId}"
        const val ARG_TASK_ID = "taskId" // Changed to uppercase with underscores

        fun createRoute(taskId: Long) = "taskDetail/$taskId"
    }
}