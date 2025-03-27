package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.NextButtom
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterLineTextField
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import oportunia.maps.frontend.taskapp.presentation.ui.components.SubtitleSection
import oportunia.maps.frontend.taskapp.presentation.viewmodel.TaskViewModel
import org.w3c.dom.Text


@Composable
fun HabilidadesScreen() {
    val habilidades = listOf("Kotlin", "Java", "Android", "Flutter", "Dart", "Python", "Laravel", "Angular", "React Native")
    var selectedHabilidad by remember { mutableStateOf("") }
    var habilidadesSeleccionadas by remember { mutableStateOf(listOf<String>()) }
    var expanded by remember { mutableStateOf(false) } // Controla la apertura del menú

    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Cuadro seleccionable en lugar de un botón
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .clickable { expanded = true }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = if (selectedHabilidad.isNotEmpty()) selectedHabilidad else "Selecciona una habilidad",
                    color = if (selectedHabilidad.isNotEmpty()) Color.Black else Color.DarkGray
                )
            }

            // Menú desplegable con habilidades
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                habilidades.forEach { habilidad ->
                    DropdownMenuItem(
                        text = { Text(habilidad) },
                        onClick = {
                            selectedHabilidad = habilidad
                            expanded = false
                        }
                    )
                }
            }

            // Espaciado entre el Box y el botón
            Spacer(modifier = Modifier.width(16.dp))

            // Botón para agregar la habilidad seleccionada
            Button(
                onClick = {
                    if (selectedHabilidad.isNotEmpty() && !habilidadesSeleccionadas.contains(
                            selectedHabilidad
                        )
                    ) {
                        habilidadesSeleccionadas = habilidadesSeleccionadas + selectedHabilidad
                        selectedHabilidad = ""  // Limpiar selección
                    }
                },
                enabled = selectedHabilidad.isNotEmpty()
            ) {
                Text("+ Agregar")
            }

        }
            Spacer(modifier = Modifier.height(16.dp))


        // Contenedor de habilidades
        Column(modifier = Modifier.fillMaxWidth()) {
            var rowItems = mutableListOf<String>()
            habilidadesSeleccionadas.forEachIndexed { index, habilidad ->
                rowItems.add(habilidad)

                val nextItemWidth = habilidad.length * 10.dp.value // Estimación del ancho del siguiente ítem
                val currentRowWidth = rowItems.sumOf { it.length * 10 }.dp.value

                // Si la fila es muy larga, agregar una nueva Row
                if (currentRowWidth + nextItemWidth > 350.dp.value || index == habilidadesSeleccionadas.size - 1) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { item ->
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                backgroundColor = Color.LightGray,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable {
                                        habilidadesSeleccionadas = habilidadesSeleccionadas - item
                                    }
                            ) {
                                Text(
                                    text = item,
                                    modifier = Modifier.padding(8.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    rowItems = mutableListOf() // Reiniciar la fila
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHabilidadesScreen() {
    HabilidadesScreen()
}




@Composable
fun RegisterStudentSecond(
    navController: NavController,
    taskViewModel: TaskViewModel,
    paddingValues: PaddingValues
) {

    var interests by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))
        SubtitleSection(stringResource(id = R.string.preparing_text))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                RegisterTextField(value = interests, onValueChange = { interests = it },
                    stringResource(id = R.string.interests_field), false, 94.dp)



            }



        }
        NextButtom(stringResource(id = R.string.next_button), onClick = {navController.navigate(
            NavRoutes.MainRegister.ROUTE)}, modifier = Modifier.width(350.dp), 350.dp)

    }
}
