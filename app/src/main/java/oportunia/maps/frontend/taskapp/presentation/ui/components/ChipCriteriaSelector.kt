package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.R

@Composable
fun ChipCriteriaSelector(
    elements: List<String>,
    selectedElement: String?,
    onSelectionChange: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    //var selectedElement by remember { mutableStateOf<String?>(null) }

    // Filtro por rating
    Row(
        verticalAlignment = Alignment.CenterVertically, // Alinea verticalmente
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre elementos
    ) {
        Text(stringResource(id = R.string.filter_rating_label), fontWeight = FontWeight.Bold)
        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.width(200.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                Text(text = selectedElement?.let { "$it" } ?: stringResource(id = R.string.choose_rating_label))
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Abrir selector"
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                elements.forEach { elememt ->
                    DropdownMenuItem(
                        text = { Text("$elememt") },
                        onClick = {
                            onSelectionChange(if (selectedElement == elememt) null else elememt)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}