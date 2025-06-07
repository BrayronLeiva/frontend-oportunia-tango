package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.presentation.ui.components.AiToggleButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.ChipCriteriaSelector
import oportunia.maps.frontend.taskapp.presentation.ui.components.InternshipDetailDialog
import oportunia.maps.frontend.taskapp.presentation.ui.components.InternshipItem
import oportunia.maps.frontend.taskapp.presentation.ui.components.InternshipRecommendedCard
import oportunia.maps.frontend.taskapp.presentation.ui.components.RatingFilterSelector
import oportunia.maps.frontend.taskapp.presentation.ui.theme.Black
import oportunia.maps.frontend.taskapp.presentation.ui.theme.DarkCyan
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RequestCreateState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RequestViewModel


@Composable
fun InternshipSearch(
    internshipLocationViewModel: InternshipLocationViewModel,
    requestViewModel: RequestViewModel,
    paddingValues: PaddingValues

) {
    var searchQuery by remember { mutableStateOf("") }
    val ratings = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
    val searchCriteriaOptions = listOf("Nombre de Compañía", "Detalles de la Pasantía")
    var selectedCriteria by remember { mutableStateOf(searchCriteriaOptions[0]) }
    var useAi by remember { mutableStateOf(false) }
    // Obtener las pasantías con las empresas y sus calificaciones
    val internships = internshipLocationViewModel.internshipsLocationList.collectAsState().value
    val recommendedinternships = internshipLocationViewModel.internshipsLocationRecommendedList.collectAsState().value

    val internshipLocationState = internshipLocationViewModel.internshipLocationState.collectAsState().value


    LaunchedEffect(Unit) {
        internshipLocationViewModel.findAllInternShipsLocations()
    }

    val context = LocalContext.current
    val requestCreateState by requestViewModel.requestCreateState.collectAsState()

    LaunchedEffect(requestCreateState) {
        when (requestCreateState) {
            is RequestCreateState.Error -> {
                val message = (requestCreateState as RequestCreateState.Error).message
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
            is RequestCreateState.Success -> {
                Toast.makeText(context, "Request sent successfully", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    var selectedInternshipLocation by remember { mutableStateOf<InternshipLocation?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    var selectedRating by remember { mutableStateOf<Double?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Título
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.internships_search_label),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            AiToggleButton(
                isAiEnabled = useAi,
                onToggle = {
                    useAi = !useAi
                    if (useAi) {
                        internshipLocationViewModel.loadInternShipsLocationsRecommended()
                    }else{
                        internshipLocationViewModel.findAllInternShipsLocations()
                    }
                }
            )
        }

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

        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            ChipCriteriaSelector(searchCriteriaOptions, selectedCriteria, onSelectionChange = {
                if (it != null) {
                    selectedCriteria = it
                }
            })
            // Filtro por rating
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Seleccionar mínimo de estrellas",
                        tint = if (selectedRating != null) Color(0xFFFFD700) else DarkCyan
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        // Filtrar las pasantías por nombre de compañía y calificación
        val filteredInternships = internships.filter {
            val companyName = it.location.company.name
            val rating = it.location.company.rating
            val matchesText = when (selectedCriteria) {
                "Nombre de Compañía" -> companyName.contains(searchQuery, ignoreCase = true)
                "Detalles de la Pasantía" -> it.internship.details.contains(searchQuery, ignoreCase = true)
                else -> true
            }
            val matchesRating = selectedRating == null || rating >= selectedRating!!
            matchesText && matchesRating
        }

        // Filtrar las pasantías por nombre de compañía y calificación
        val filteredRecommendedInternships = recommendedinternships.filter {
            val companyName = it.locationCompany.company.name
            val rating = it.locationCompany.company.rating
            val matchesText = when (selectedCriteria) {
                "Nombre de Compañía" -> companyName.contains(searchQuery, ignoreCase = true)
                "Detalles de la Pasantía" -> it.internship.details.contains(searchQuery, ignoreCase = true)
                else -> true
            }
            val matchesRating = selectedRating == null || rating >= selectedRating!!
            matchesText && matchesRating
        }


        // Handle the different internship states
        when (val state = internshipLocationState) {
            is InternshipLocationState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is InternshipLocationState.Empty -> {
                androidx.compose.material3.Text(
                    text = "No internships locations available.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            is InternshipLocationState.Success -> {
                if(useAi) {
                    LazyColumn {
                        items(filteredRecommendedInternships) { internshipLocation ->
                            InternshipRecommendedCard(internshipLocation, onClick = {
                                //selectedInternshipLocation = it
                                showDialog = true
                            })
                        }
                    }
                }else {
                    LazyColumn {
                        items(filteredInternships) { internshipLocation ->
                            InternshipItem(internshipLocation, onClick = {
                                selectedInternshipLocation = it
                                showDialog = true
                            })
                        }
                    }
                }
            }
            is InternshipLocationState.Error -> {
                androidx.compose.material3.Text(
                    text = "Error: ${state.message}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Mostrar dialog con detalle si showDialog es true
        if (showDialog && selectedInternshipLocation != null) {
            InternshipDetailDialog(
                internshipLocation = selectedInternshipLocation!!,
                onDismiss = { showDialog = false },
                onRequestClick = {
                    requestViewModel.createRequest(selectedInternshipLocation!!)
                    showDialog = false
                }
            )
        }
        if (expanded) {
            RatingFilterSelector(
                ratings = ratings,
                selectedRating = selectedRating,
                onRatingSelected = { selectedRating = it },
                onDismiss = { expanded = false }
            )
        }

    }
}



@Composable
@Preview
fun InternshipSearchPreview(){
    //InternshipSearch()
}