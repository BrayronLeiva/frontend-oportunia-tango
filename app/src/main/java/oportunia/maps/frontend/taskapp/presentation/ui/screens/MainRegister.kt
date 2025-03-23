package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes


@Composable
fun MainRegister(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Sección de títulos (arriba)
        Spacer(modifier = Modifier.height(40.dp)) // Agrega espacio desde arriba
        Text("Oportunia", style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(40.dp))
        Text("Crea una cuenta", style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
        Text("Ingrese los datos solicitados", style = MaterialTheme.typography.subtitle1)
        Spacer(modifier = Modifier.height(16.dp)) // Espacio antes de los TextFields

        // 🔹 Sección de campos de texto (centrado)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f), // Ocupa el espacio restante para centrar los campos
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RegisterTextField(value = email, onValueChange = { email = it }, label = "Email")
                RegisterTextField(value = password, onValueChange = { password = it }, label = "Password")
                RegisterTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = "Confirm Password", singleLine = false)
            }
        }

        // 🔹 Botón en la parte inferior
        Button(
            onClick = { navController.navigate("siguiente_pantalla") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Registrar")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewRegistroScreen() {
    // Simula el NavController para la vista previa
    val navController = rememberNavController()

    MainRegister(navController = navController)
}
