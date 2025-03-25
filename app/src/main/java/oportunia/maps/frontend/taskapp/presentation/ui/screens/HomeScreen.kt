package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes

@Composable
fun HomeScreen(navController: NavHostController, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(NavRoutes.StudentMap.ROUTE) }) {
            Text("Go to Student Map")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(NavRoutes.CompanyMap.ROUTE) }) {
            Text("Go to Company Map")
        }
    }
}
