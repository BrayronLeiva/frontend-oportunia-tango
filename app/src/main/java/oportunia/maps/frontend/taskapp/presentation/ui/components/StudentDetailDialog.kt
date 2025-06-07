package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oportunia.maps.frontend.taskapp.domain.model.Request
import oportunia.maps.frontend.taskapp.domain.model.Student

@Composable
fun StudentDetailDialog(
    student: Student,
    requestList: List<Request>,
    onDismiss: () -> Unit,
    onRequestAction: (Request) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = student.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                InfoSection(label = "Nombre", value = student.name)
                InfoSection(label = "Indentificacion", value = student.identification)
                InfoSection(label = "Informacion Personal", value = student.personalInfo)
                InfoSection(label = "Rating", value = "${student.rating} ★")

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Solicitudes del estudiante",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                requestList.forEach { request ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp) // espacio interno general
                        ) {
                            Text(
                                text = request.internshipLocation.internship.details,
                                modifier = Modifier.padding(bottom = 12.dp) // separación del texto superior
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Estado: ${if (request.state) "Aceptado" else "Pendiente"}",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Icon(
                                    imageVector = if (request.state) Icons.Default.CheckCircle else Icons.Default.Error,
                                    contentDescription = if (request.state) "Aprobado" else "Rechazado",
                                    tint = if (request.state) Color(0xFF4CAF50) else Color(0xFFF44336),
                                    modifier = Modifier.size(15.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = { onRequestAction(request) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (request.state) Color.Red else Color.Cyan,
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier.width(150.dp)
                                ) {
                                    Text(
                                        text = if (request.state) "Cancelar" else "Aceptar",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
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
