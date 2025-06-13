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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentRecommendedDto
import oportunia.maps.frontend.taskapp.domain.model.Student
import oportunia.maps.frontend.taskapp.presentation.ui.components.AiToggleButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.ChipCriteriaSelector
import oportunia.maps.frontend.taskapp.presentation.ui.components.RatingFilterSelector
import oportunia.maps.frontend.taskapp.presentation.ui.components.StudentCard
import oportunia.maps.frontend.taskapp.presentation.ui.components.StudentDetailDialog
import oportunia.maps.frontend.taskapp.presentation.ui.components.StudentDetailRecommendedDialog
import oportunia.maps.frontend.taskapp.presentation.ui.components.StudentRecommenedCard
import oportunia.maps.frontend.taskapp.presentation.ui.theme.DarkCyan
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RequestUpdateState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RequestViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentListState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel

@Composable
fun StudentSearchScreen(
    navController: NavController,
    studentViewModel: StudentViewModel,
    requestViewModel: RequestViewModel,
    paddingValues: PaddingValues,
    onStudentSelected: (Student) -> Unit // callback para devolver seleccionado
) {
    var searchQuery by remember { mutableStateOf("") }
    val ratings = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
    val searchCriteriaOptions = listOf(stringResource(id = R.string.name), stringResource(id = R.string.identification))
    var selectedCriteria by remember { mutableStateOf(searchCriteriaOptions[0]) }

    var useAi by remember { mutableStateOf(false) }
    // Obtener las pasantías con las empresas y sus calificaciones
    val students = studentViewModel.studentList.collectAsState().value
    val studentsRecommended = studentViewModel.studentRecommendedList.collectAsState().value
    val studentListState = studentViewModel.studentListState.collectAsState()
    val requestList = requestViewModel.requestList.collectAsState()
    //val internshipLocationState = internshipLocationViewModel.internshipLocationState.collectAsState().value

    val requestUpdateState = requestViewModel.requestUpdateState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        studentViewModel.loadStudentsRequestingToMyCompany()

    }
    LaunchedEffect(requestUpdateState.value) {
        when (requestUpdateState.value) {
            is RequestUpdateState.Error -> {
                val message = (requestUpdateState as RequestUpdateState.Error).message
                //Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
            is RequestUpdateState.Success -> {
                //Toast.makeText(context, "Request update successfully", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    var selectedStudent by remember { mutableStateOf<Student?>(null) }
    var selectedRecommendedStudent by remember { mutableStateOf<StudentRecommendedDto?>(null) }
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
                text = stringResource(id = R.string.student_search_label),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            AiToggleButton(
                isAiEnabled = useAi,
                onToggle = {
                    useAi = !useAi
                    if (useAi) {
                        studentViewModel.loadStudentsRecommended()
                    }else{
                        studentViewModel.loadStudentsRequestingToMyCompany()
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
                        stringResource(id = R.string.name) -> stringResource(id = R.string.search_by_company_name)
                        stringResource(id = R.string.internship) -> stringResource(id = R.string.search_by_internship_details)
                        else -> stringResource(id = R.string.search_label)
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
                        contentDescription = stringResource(id = R.string.filter_rating_label),
                        tint = if (selectedRating != null) Color(0xFFFFD700) else DarkCyan
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


        // Filtrar las pasantías por nombre de compañía y calificación
        val filteredRecommendedStudents = studentsRecommended.filter {
            val rating = it.rating
            val name = it.name
            val identification = it.identification

            val matchesText = when (selectedCriteria) {
                stringResource(id = R.string.name) -> name.contains(searchQuery, ignoreCase = true)
                stringResource(id = R.string.identification) -> identification.contains(searchQuery, ignoreCase = true)
                else -> true
            }


            val matchesRating = selectedRating == null || rating >= selectedRating!!
            matchesText && matchesRating

        }

        // Filtrar las pasantías por nombre de compañía y calificación
        val filteredStudents = students.filter {
            val rating = it.rating
            val name = it.name
            val identification = it.identification

            val matchesText = when (selectedCriteria) {
                stringResource(id = R.string.name) -> name.contains(searchQuery, ignoreCase = true)
                stringResource(id = R.string.identification) -> identification.contains(searchQuery, ignoreCase = true)
                else -> true
            }


            val matchesRating = selectedRating == null || rating >= selectedRating!!
            matchesText && matchesRating
        }

        // Handle the different internship states
        when (val state = studentListState.value) {
            is StudentListState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is StudentListState.Empty -> {
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.no_student_info_available),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            is StudentListState.Success -> {
                if(useAi) {

                    LazyColumn {
                        items(filteredRecommendedStudents) { studentRecommended ->
                            StudentRecommenedCard(studentRecommended, onClick = {
                                selectedRecommendedStudent = it
                                showDialog = true
                            })
                        }
                    }
                }else {

                    LazyColumn {
                        items(filteredStudents) { student ->
                            StudentCard(student, onClick = {
                                selectedStudent = it
                                showDialog = true
                            })
                        }
                    }
                }
            }
            is StudentListState.Error -> {
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.error_message, state.message),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Mostrar dialog con detalle si showDialog es true
        if (showDialog && selectedStudent != null && !useAi) {
            //requestViewModel.findAllRequest()
            requestViewModel.findRequestsbyStudentAndCompany(selectedStudent!!.id)
            StudentDetailDialog(
                navController = navController,
                student = selectedStudent!!,
                requestList = requestList.value,
                onDismiss = { showDialog = false },
                onRequestAction = { request ->
                    requestViewModel.updateRequest(request)

                    //showDialog = false
                }
            )
        }
        if (showDialog && selectedRecommendedStudent != null && useAi) {
            requestViewModel.findRequestsbyStudentAndCompany(selectedRecommendedStudent!!.id)
            StudentDetailRecommendedDialog(
                student = selectedRecommendedStudent!!,
                requestList = requestList.value,
                onDismiss = { showDialog = false },
                onRequestAction = { request ->
                    requestViewModel.updateRequest(request)
                    //showDialog = false

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
