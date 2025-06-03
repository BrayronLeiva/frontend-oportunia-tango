package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.presentation.navigation.BottomNavItem

@Composable
fun BottomNavigationBar(navController: NavController, userRole: TypeUser, email: String) {
    val items = BottomNavItem.items(userRole, email)

    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                selected = false, // You can manage the selected state as needed
                onClick = { navController.navigate(item.route) },
                label = { Text(stringResource(id = item.title)) },
                icon = { Icon(imageVector = item.icon, contentDescription = stringResource(id = item.title)) }
            )
        }
    }
}