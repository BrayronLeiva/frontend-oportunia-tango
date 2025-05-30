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

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation


@Composable
fun InternshipDetailDialog(
    internshipLocation: InternshipLocation,
    onDismiss: () -> Unit,
    onConfirm: (InternshipLocation) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = internshipLocation.location.company.name, fontWeight = FontWeight.Bold,fontSize = 20.sp)
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                InfoSection(label = "Descripción", value = internshipLocation.location.company.description)
                InfoSection(label = "Misión", value = internshipLocation.location.company.mision)
                InfoSection(label = "Visión", value = internshipLocation.location.company.vision)
                InfoSection(label = "Cultura Corporativa", value = internshipLocation.location.company.corporateCultur)
                InfoSection(label = "Contacto", value = internshipLocation.location.company.contact.toString())
                InfoSection(label = "Rating", value = "${internshipLocation.location.company.rating} ★")
                InfoSection(label = "Tipo de Pasantía", value = internshipLocation.location.company.internshipType.toString())

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Detalles de la Pasantía",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = internshipLocation.internship.details,
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
                    onClick = { onConfirm(internshipLocation) },
                    width = 140.dp
                )
            }
        }
    )
}

@Composable
fun InfoSection(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = "$label:", fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}