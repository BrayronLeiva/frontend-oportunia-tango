package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.ui.theme.DarkCyan


@Composable
fun SelectionTagInput(habilidades: List<String>) {
    var selectedHabilidad by remember { mutableStateOf("") }
    var habilidadesSeleccionadas by remember { mutableStateOf(listOf<String>()) }
    var expanded by remember { mutableStateOf(false) } // Controla la apertura del menú

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = stringResource(id = R.string.chosee_skills_field),
            style = MaterialTheme.typography.body2,
            color = Color.Black,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )

        Row(modifier = Modifier.padding(16.dp)) {
            ItemSelector(
                selectedHabilidad = selectedHabilidad,
                onClick = { expanded = true } // Acción cuando se haga clic en el Box
            )

            // Menú desplegable con habilidades
            ItemsList(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                habilidades = habilidades,
                onHabilidadSelected = { habilidad ->
                    selectedHabilidad = habilidad
                    expanded = false
                }
            )

            // Espaciado entre el Box y el botón
            Spacer(modifier = Modifier.width(16.dp))

            // Botón para agregar la habilidad seleccionada
            AddButton(
                selectedHabilidad = selectedHabilidad,
                habilidadesSeleccionadas = habilidadesSeleccionadas,
                onHabilidadAgregada = { habilidad ->
                    habilidadesSeleccionadas = habilidadesSeleccionadas + habilidad
                    selectedHabilidad = ""
                }
            )

        }
        Spacer(modifier = Modifier.height(16.dp))


        TagBox(
            habilidadesSeleccionadas = habilidadesSeleccionadas,
            onTagRemoved = { habilidad ->
                habilidadesSeleccionadas = habilidadesSeleccionadas - habilidad
            }
        )
    }
}

@Composable
fun ItemSelector(
    selectedHabilidad: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(50.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = if (selectedHabilidad.isNotEmpty()) selectedHabilidad else "Select a skill",
            color = if (selectedHabilidad.isNotEmpty()) Color.Black else Color.DarkGray
        )
    }
}

@Composable
fun ItemsList(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    habilidades: List<String>,
    onHabilidadSelected: (String) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        habilidades.forEach { habilidad ->
            DropdownMenuItem(
                text = { Text(habilidad) },
                onClick = {
                    onHabilidadSelected(habilidad)
                    onDismissRequest() // Cierra el menú al seleccionar una habilidad
                }
            )
        }
    }
}


@Composable
fun AddButton(
              selectedHabilidad: String,
              habilidadesSeleccionadas: List<String>,
              onHabilidadAgregada: (String) -> Unit
) {
    Button(
        onClick = {
            if (selectedHabilidad.isNotEmpty() && !habilidadesSeleccionadas.contains(selectedHabilidad)) {
                onHabilidadAgregada(selectedHabilidad)
            }
        },
        enabled = selectedHabilidad.isNotEmpty()

    ) {
        Text("+")
    }
}


@Composable
fun TagBox(
    habilidadesSeleccionadas: List<String>,
    onTagRemoved: (String) -> Unit
) {
    // Contenedor de habilidades
    Column(modifier = Modifier.fillMaxWidth()) {
        var rowItems = mutableListOf<String>()
        habilidadesSeleccionadas.forEachIndexed { index, habilidad ->
            rowItems.add(habilidad)

            // Estimación del ancho del siguiente ítem
            val nextItemWidth = habilidad.length * 10.dp.value
            // Ancho total de los ítems de la fila actual
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
                            backgroundColor = DarkCyan,
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    onTagRemoved(item)
                                }
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier.padding(8.dp)
                                .wrapContentWidth(Alignment.Start),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                rowItems = mutableListOf()
            }
        }
    }
}



@Preview
@Composable
fun PreviewSelectionTagInput() {
    //SelectionTagInput()
}