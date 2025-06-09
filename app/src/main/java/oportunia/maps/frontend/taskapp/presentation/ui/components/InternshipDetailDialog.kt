package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll

import androidx.compose.material.Text

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationFlagDto
import oportunia.maps.frontend.taskapp.domain.model.Internship
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationFlagState


@Composable
fun InternshipDetailDialog(
    internshipLocation: InternshipLocation,
    onDismiss: () -> Unit,
    onRequestClick: (Internship) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = internshipLocation.location.company.name, fontWeight = FontWeight.Bold,fontSize = 20.sp)
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                InfoSection(label = stringResource(id = R.string.description), value = internshipLocation.location.company.description)
                InfoSection(label = stringResource(id = R.string.mission), value = internshipLocation.location.company.mision)
                InfoSection(label = stringResource(id = R.string.vision), value = internshipLocation.location.company.vision)
                InfoSection(label = stringResource(id = R.string.corporate_culture), value = internshipLocation.location.company.corporateCultur)
                InfoSection(label = stringResource(id = R.string.contact), value = internshipLocation.location.company.contact.toString())
                InfoSection(label = stringResource(id = R.string.rating), value = "${internshipLocation.location.company.rating} ★")
                InfoSection(label = stringResource(id = R.string.internship_type), value = internshipLocation.location.company.internshipType.toString())



                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(id = R.string.internship_details),
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
                    text = stringResource(id = R.string.back_button),
                    onClick = onDismiss,
                    width = 140.dp
                )
                CustomButton(
                    text = stringResource(id = R.string.apply),
                    onClick = { onRequestClick(internshipLocation.internship) },
                    width = 140.dp
                )
            }
        }
    )
}

