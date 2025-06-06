package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.domain.model.Request


@Composable
fun RequestCardDetailDialog(
    request: Request,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = request.internshipLocation.location.company.name, fontWeight = FontWeight.Bold,fontSize = 20.sp)
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                // Ícono que representa el estado
                Icon(
                    imageVector = if (request.state) Icons.Default.CheckCircle else Icons.Default.Error,
                    contentDescription = if (request.state) "Aprobado" else "Rechazado",
                    tint = if (request.state) Color(0xFF4CAF50) else Color(0xFFF44336), // verde o rojo
                    modifier = Modifier.size(48.dp)
                )
                InfoSection(label = "Estado", value = request.state.toString())
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Detalles de la Pasantía",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = request.internshipLocation.internship.details,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomButton(
                    text = "Cerrar",
                    onClick = onDismiss,
                    width = 140.dp
                )
            }
        }
    )
}

