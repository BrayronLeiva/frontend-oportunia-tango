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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.domain.model.Request
import oportunia.maps.frontend.taskapp.presentation.ui.components.ChipCriteriaSelector
import oportunia.maps.frontend.taskapp.presentation.ui.components.RequestCard
import oportunia.maps.frontend.taskapp.presentation.ui.components.RequestCardDetailDialog
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RequestState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RequestViewModel

@Composable
fun RequestDetailScreen(
    requestViewModel: RequestViewModel,
    paddingValues: PaddingValues,
    onRequestSelected: (Request) -> Unit // callback para devolver seleccionado
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchCriteriaOptions = listOf("Company Name", "Internships Details")
    var selectedCriteria by remember { mutableStateOf(searchCriteriaOptions[0]) }
    var useAi by remember { mutableStateOf(false) }
    // Obtener las pasantías con las empresas y sus calificaciones

    val requestState by requestViewModel.requestState.collectAsState()
    val requestList by requestViewModel.requestList.collectAsState()

    LaunchedEffect(Unit) {
        requestViewModel.findAllRequest()
    }

    var selectedRequest by remember { mutableStateOf<Request?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Título
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.request_search_label),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            //Here we can items next to the title
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = {
                Text(
                    when (selectedCriteria) {
                        stringResource(id = R.string.company_name) -> stringResource(id = R.string.search_by_company_name)
                        stringResource(id = R.string.internship_details) -> stringResource(R.string.search_by_internship_details)
                        else -> stringResource(id = R.string.search_by_company_name)
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

        }

        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))

        // Filtrar las pasantías por nombre de compañía y calificación
        val filteredRequests = requestList.filter {
            val companyName = it.internshipLocation.location.company.name
            //Aca agregar mejores criterios

            val matchesText = when (selectedCriteria) {
                stringResource(id = R.string.company_name) -> companyName.contains(searchQuery, ignoreCase = true)
                stringResource(id = R.string.internship_details) -> it.internshipLocation.internship.details.contains(searchQuery, ignoreCase = true)
                else -> true
            }
            matchesText
        }



        // Handle the different internship states
        when (val state = requestState) {
            is RequestState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is RequestState.Empty -> {
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.no_requests_available),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            is RequestState.Success -> {
                if (state.internships.isNotEmpty()) {
                    LazyColumn {
                        items(filteredRequests) { request ->
                            RequestCard(request, onClick = {
                                selectedRequest = it
                                showDialog = true
                            })
                        }
                    }
                } else {
                    androidx.compose.material3.Text(
                        text = stringResource(id = R.string.no_internships_available),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            is RequestState.Error -> {
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.error_message, state.message),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }


        // Mostrar dialog con detalle si showDialog es true
        if (showDialog && selectedRequest != null) {
            RequestCardDetailDialog(
                request = selectedRequest!!,
                onDismiss = { showDialog = false }
            )
        }

    }
}
