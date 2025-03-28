package oportunia.maps.frontend.taskapp.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.data.datasource.model.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.data.datasource.userrole.UserRoleProvider

sealed class BottomNavItem(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    companion object {
        fun items(email: String) = listOf(
            Map(email), Search, Profile
        )

        private fun getMapRoute(email: String): String {
            val userRole: TypeUser? = UserRoleProvider.getUserRoleByEmail(email)

            return when (userRole) {
                TypeUser.STU -> NavRoutes.StudentMap.ROUTE
                TypeUser.COM -> NavRoutes.CompanyMap.ROUTE
                else -> NavRoutes.Login.ROUTE // Fallback route
            }
        }

        data class Map(val email: String) : BottomNavItem(
            getMapRoute(email), // Dynamic route based on email
            R.string.map,
            Icons.Filled.Place
        )

        data object Search : BottomNavItem(
            "search", // Placeholder for now
            R.string.search,
            Icons.Filled.Search
        )

        data object Profile : BottomNavItem(
            NavRoutes.Profile.ROUTE,
            R.string.profile,
            Icons.Filled.Person
        )
    }
}