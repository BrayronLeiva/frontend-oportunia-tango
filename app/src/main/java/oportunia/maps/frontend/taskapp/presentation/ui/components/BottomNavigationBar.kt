package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import oportunia.maps.frontend.taskapp.presentation.navigation.BottomNavItem

@Composable
fun BottomNavigationBar(navController: NavController, email: String) {
    val items = BottomNavItem.items(email)

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