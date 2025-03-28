package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun InternshipSearch() {
    var searchQuery by remember { mutableStateOf("") }
    //var selectedRating by remember { mutableStateOf(0) }
    val ratings = listOf(1, 2, 3, 4, 5)


    var expanded by remember { mutableStateOf(false) }
    var selectedRating by remember { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Título
        Text("Búsqueda de Pasantías", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar compañía") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Filtro por rating
        // Filtro por rating usando OutlinedButton
        Row (
            verticalAlignment = Alignment.CenterVertically, // Alinea verticalmente
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre elementos
        ){
            Text("Filtrar por calificación", fontWeight = FontWeight.Bold)
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = selectedRating?.let { "$it ★" } ?: "Seleccionar calificación")
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

        // Resultados (mock data)
        val internships = listOf(
            Internship("Google", 5),
            Internship("Microsoft", 4),
            Internship("Facebook", 3),
            Internship("Amazon", 4),
            Internship("Netflix", 5)
        )

        val filteredInternships = internships.filter {
            (searchQuery.isEmpty() || it.company.contains(searchQuery, ignoreCase = true)) &&
                    (selectedRating == null || it.rating == selectedRating) // Si `selectedRating` es null, no filtra por rating
        }

        LazyColumn {
            items(filteredInternships) { internship ->
                InternshipItem(internship)
            }
        }
    }
}

@Composable
fun InternshipItem(internship: Internship) {
    Card (
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = "Compañía", modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(internship.company, fontWeight = FontWeight.Bold)
                Text("${internship.rating} ★", color = Color.Gray)
            }
        }
    }
}

data class Internship(val company: String, val rating: Int)

@Composable
@Preview
fun InternshipSearchPreview(){
    InternshipSearch()
}