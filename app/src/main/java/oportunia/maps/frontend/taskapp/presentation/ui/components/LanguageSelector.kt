package oportunia.maps.frontend.taskapp.presentation.ui.components

import android.app.Activity
import android.util.Log
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun LanguageSelector() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val languages = listOf(
        "English" to Locale("en"),
        "Español" to Locale("es"),
        "Français" to Locale("fr"),
        "Nederlands" to Locale("nl")
    )

    Box {
        Icon(
            imageVector = Icons.Default.Language,
            contentDescription = "Select Language",
            modifier = Modifier
                .size(30.dp)
                .clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { (name, locale) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        expanded = false
                        updateLocale(context, locale)
                        (context as? Activity)?.recreate()
                    }
                )
            }
        }
    }
}