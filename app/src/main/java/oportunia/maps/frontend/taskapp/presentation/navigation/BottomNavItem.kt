package oportunia.maps.frontend.taskapp.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser

sealed class BottomNavItem(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    companion object {
        fun items(userRole: TypeUser, email: String) = listOf(
            Map(userRole, email), Search, Profile
        )

        private fun getMapRoute(userRole: TypeUser): String {
            return when (userRole) {
                TypeUser.STU -> NavRoutes.StudentMap.ROUTE
                TypeUser.COM -> NavRoutes.CompanyMap.ROUTE
                else -> NavRoutes.Login.ROUTE
            }
        }

        data class Map(val userRole: TypeUser, val email: String) : BottomNavItem(
            getMapRoute(userRole),
            R.string.map,
            Icons.Filled.Place
        )

        data object Search : BottomNavItem(
            "search",
            R.string.search,
            Icons.Filled.Search
        )

        data object Profile : BottomNavItem(
            NavRoutes.StudentProfile.ROUTE,
            R.string.profile,
            Icons.Filled.Person
        )
    }
}