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
import oportunia.maps.frontend.taskapp.presentation.ui.components.InternshipItem
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel


@Composable
fun InternshipSearch(
    internshipLocationViewModel: InternshipLocationViewModel,
    paddingValues: PaddingValues
) {
    var searchQuery by remember { mutableStateOf("") }
    val ratings = listOf(1.0, 2.0, 3.0, 4.0, 5.0)

    // Obtener las pasantías con las empresas y sus calificaciones
    val internships = internshipLocationViewModel.internshipsLocationList.collectAsState().value

    LaunchedEffect(Unit) {
        internshipLocationViewModel.findAllInternShipsLocations()
    }

    var expanded by remember { mutableStateOf(false) }
    var selectedRating by remember { mutableStateOf<Double?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Título
        Spacer(modifier = Modifier.height(32.dp))

        Text(stringResource(id = R.string.internships_search_label), fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { stringResource(id = R.string.search_company_label) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = stringResource(id = R.string.search_label)) },
            modifier = Modifier.fillMaxWidth()
        )

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

            (searchQuery.isEmpty() || companyName.contains(searchQuery, ignoreCase = true)) &&
                    (selectedRating == null || selectedRating == rating)
        }

        LazyColumn {
            items(filteredInternships) { internship ->
                InternshipItem(internship)
            }
        }
    }
}



@Composable
@Preview
fun InternshipSearchPreview(){
    //InternshipSearch()
}