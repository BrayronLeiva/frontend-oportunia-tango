package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.theme.DarkCyan

@Composable
fun BottomNavigationRow(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = DarkCyan,
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            selected = false, // Aquí puedes usar el estado para indicar cuál está seleccionado
            onClick = {  navController.navigate(NavRoutes.StudentMap.ROUTE) }, // Navegar a la pantalla correspondiente
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = { navController.navigate(NavRoutes.InternshipsSearch.ROUTE) },
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = { navController.navigate(NavRoutes.StudentProfile.ROUTE) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }
        )
    }
}