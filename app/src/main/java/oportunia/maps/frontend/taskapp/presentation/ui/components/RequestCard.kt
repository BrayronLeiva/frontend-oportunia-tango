package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.domain.model.Request
import oportunia.maps.frontend.taskapp.presentation.ui.theme.DarkCyan


@Composable
fun RequestCard(
    request: Request,
    onClick: (Request) -> Unit) {
    // Obtener los datos de la compañía y la calificación
    val companyName = request.internshipLocation.location.company.name
    val internshipDetail = request.internshipLocation.internship.details
    val state = request.state

    Card(modifier = Modifier
        .fillMaxWidth().padding(vertical = 8.dp).clickable { onClick(request) }) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = "Compañía", modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                //Text(internshipDetail, fontWeight = FontWeight.ExtraBold)
                Text(companyName, fontWeight = FontWeight.Bold, color = DarkCyan)
                Text("$state ★", color = Color.Gray)

            }


            // Ícono que representa el estado
            Icon(
                imageVector = if (state) Icons.Default.CheckCircle else Icons.Default.Error,
                contentDescription = if (state) "Aprobado" else "Rechazado",
                tint = if (state) Color(0xFF4CAF50) else Color(0xFFF44336), // verde o rojo
                modifier = Modifier.size(24.dp)
            )
        }
    }
}