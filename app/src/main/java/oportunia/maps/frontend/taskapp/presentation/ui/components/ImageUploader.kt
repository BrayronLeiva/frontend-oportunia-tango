package oportunia.maps.frontend.taskapp.presentation.ui.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.ui.theme.Black
import oportunia.maps.frontend.taskapp.presentation.ui.theme.DarkCyan
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RegisterViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel
import java.io.File


@Composable
fun ImageUploader(studentId: Long, viewModel: RegisterViewModel) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val file = uriToFile(uri, context)
            if (file != null) {
                viewModel.uploadStudentImage(studentId, file)
            }
        }
    }

    Button(
        onClick = { launcher.launch("image/*") },
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkCyan,
            contentColor = Black
        )

    ) {
        Text(
            text = stringResource(id = R.string.load_image),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}

fun uriToFile(uri: Uri, context: Context): File? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
    tempFile.outputStream().use { fileOut ->
        inputStream.copyTo(fileOut)
    }
    return tempFile
}


