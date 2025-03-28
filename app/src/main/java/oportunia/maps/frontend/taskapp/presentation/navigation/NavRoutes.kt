package oportunia.maps.frontend.taskapp.presentation.navigation

/**
 * Contains all navigation route constants for the application.
 *
 * Using a sealed class with objects ensures type safety and prevents errors from misspelled route strings.
 */
sealed class NavRoutes {
    data object MainRegister : NavRoutes(){
        const val ROUTE = "mainRegister"
    }

    data object RegisterStudentFirst : NavRoutes(){
        const val ROUTE = "registerStudentFirst"
    }

    data object RegisterStudentSecond : NavRoutes(){
        const val ROUTE = "registerStudentSecond"
    }

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

    data object Login : NavRoutes() {
        const val ROUTE = "login"
    }

    data object Profile : NavRoutes() {
        const val ROUTE = "profile"
    }

    data object LocationCompanyDetail : NavRoutes() {
        const val ROUTE = "locationCompanyDetail/{locationCompanyId}"
        const val ARG_LOCATION_COMPANY_ID = "locationCompanyId" // Changed to uppercase with underscores

        fun createRoute(locationCompanyId: Long) = "locationCompanyDetail/$locationCompanyId"
    }

    data object InternshipListStudent : NavRoutes() {
        const val ROUTE = "internshipStudent/{locationCompanyId}"
        const val ARG_LOCATION_COMPANY_ID = "locationCompanyId" // Changed to uppercase with underscores

        fun createRoute(locationCompanyId: Long) = "internshipStudent/$locationCompanyId"
    }

    data object InternshipListCompany : NavRoutes() {
        const val ROUTE = "internshipCompany/{locationCompanyId}"
        const val ARG_LOCATION_COMPANY_ID = "locationCompanyId" // Changed to uppercase with underscores

        fun createRoute(locationCompanyId: Long) = "internshipCompany/$locationCompanyId"
    }

    data object TaskDetail : NavRoutes() {
        const val ROUTE = "taskDetail/{taskId}"
        const val ARG_TASK_ID = "taskId" // Changed to uppercase with underscores

        fun createRoute(taskId: Long) = "taskDetail/$taskId"
    }

}