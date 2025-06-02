package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.presentation.ui.components.ChipCriteriaSelector
import oportunia.maps.frontend.taskapp.presentation.ui.components.InternshipDetailDialog
import oportunia.maps.frontend.taskapp.presentation.ui.components.InternshipItem
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel


@Composable
fun InternshipSearch(
    internshipLocationViewModel: InternshipLocationViewModel,
    paddingValues: PaddingValues,
    onInternshipSelected: (InternshipLocation) -> Unit // callback para devolver seleccionado
) {
    var searchQuery by remember { mutableStateOf("") }
    val ratings = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
    val searchCriteriaOptions = listOf("Nombre de Compañía", "Detalles de la Pasantía")
    var selectedCriteria by remember { mutableStateOf(searchCriteriaOptions[0]) }

    // Obtener las pasantías con las empresas y sus calificaciones
    val internships = internshipLocationViewModel.internshipsLocationList.collectAsState().value

    LaunchedEffect(Unit) {
        internshipLocationViewModel.findAllInternShipsLocations()
    }

    var selectedInternshipLocation by remember { mutableStateOf<InternshipLocation?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    var selectedRating by remember { mutableStateOf<Double?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Título
        Spacer(modifier = Modifier.height(32.dp))

        Text(stringResource(id = R.string.internships_search_label), fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))


        Spacer(modifier = Modifier.height(16.dp))
        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = {
            Text(
                when (selectedCriteria) {
                    "Nombre de Compañía" -> "Buscar por nombre de compañía"
                    "Detalles de la Pasantía" -> "Buscar por detalles de la pasantía"
                    else -> "Buscar"
                }
            )
        },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = stringResource(id = R.string.search_label)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        ChipCriteriaSelector(searchCriteriaOptions,selectedCriteria, onSelectionChange = {
            if (it != null) {
                selectedCriteria = it
            }
        })

        Spacer(modifier = Modifier.height(16.dp))

        // Filtro por rating
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alinea verticalmente
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre elementos
        ) {
            Text(stringResource(id = R.string.filter_rating_label), fontWeight = FontWeight.Bold)
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(text = selectedRating?.let { "$it ★" } ?: stringResource(id = R.string.choose_rating_label))
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    ratings.forEach { rating ->
                        DropdownMenuItem(
                            text = { Text("$rating ★") },
                            onClick = {
                                selectedRating = if (selectedRating == rating) null else rating
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filtrar las pasantías por nombre de compañía y calificación
        val filteredInternships = internships.filter {
            val companyName = it.location.company.name
            val rating = it.location.company.rating
            when (selectedCriteria) {
                "Nombre de Compañía" -> it.location.company.name.contains(searchQuery, ignoreCase = true)
                "Detalles de la Pasantía" -> it.internship.details.contains(searchQuery, ignoreCase = true)
                else -> true
            }
        }

        LazyColumn {
            items(filteredInternships) { internshipLocation ->
                InternshipItem(internshipLocation, onClick = {
                    selectedInternshipLocation = it
                    showDialog = true
                })
            }
        }
        // Mostrar dialog con detalle si showDialog es true
        if (showDialog && selectedInternshipLocation != null) {
            InternshipDetailDialog(
                internshipLocation = selectedInternshipLocation!!,
                onDismiss = { showDialog = false },
                onConfirm = {
                    onInternshipSelected(it)
                    showDialog = false
                }
            )
        }
    }
}



@Composable
@Preview
fun InternshipSearchPreview(){
    //InternshipSearch()
}