package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentRecommendedDto


@Composable
fun StudentDetailRecommendedDialog(
    student: StudentRecommendedDto,
    onDismiss: () -> Unit,
    onConfirm: (StudentRecommendedDto) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = student.name, fontWeight = FontWeight.Bold,fontSize = 20.sp)
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                InfoSection(label = "Nombre", value = student.name)
                InfoSection(label = "Indentificacion", value = student.identification)
                InfoSection(label = "Experiencia", value = student.experience)
                InfoSection(label = "Informacion Personal", value = student.personalInfo)
                InfoSection(label = "Rating", value = "${student.rating} ★")


                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Descripcion",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = student.personalInfo,
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
                CustomButton(
                    text = "Aplicar",
                    onClick = { onConfirm(student) },
                    width = 140.dp
                )
            }
        }
    )
}
