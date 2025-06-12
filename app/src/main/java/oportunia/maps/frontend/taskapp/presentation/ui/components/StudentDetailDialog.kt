package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.domain.model.Request
import oportunia.maps.frontend.taskapp.domain.model.Student

@Composable
fun StudentDetailDialog(
    navController: NavController,
    student: Student,
    requestList: List<Request>,
    onDismiss: () -> Unit,
    onRequestAction: (Request) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = student.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Button(
                    onClick = { navController.navigate("rateStudent/${student.id}") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(stringResource(id = R.string.rate_button))
                }
            }
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                InfoSection(label = stringResource(R.string.name), value = student.name)
                InfoSection(label = stringResource(R.string.identification), value = student.identification)
                InfoSection(label = stringResource(R.string.personal_info), value = student.personalInfo)
                InfoSection(label = stringResource(R.string.rating_format), value =  student.rating.toString())

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.student_requests_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                requestList.forEach { request ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = request.internshipLocation.internship.details,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(
                                        R.string.state_format,
                                        stringResource(if (request.state) R.string.approved else R.string.pending)
                                    ),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Icon(
                                    imageVector = if (request.state) Icons.Default.CheckCircle else Icons.Default.Error,
                                    contentDescription = stringResource(
                                        if (request.state) R.string.approved else R.string.denied
                                    ),
                                    tint = if (request.state) Color(0xFF4CAF50) else Color(0xFFF44336),
                                    modifier = Modifier.size(15.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = { onRequestAction(request) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (request.state) Color.Red else Color.Cyan,
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier.width(150.dp)
                                ) {
                                    Text(
                                        text = stringResource(
                                            if (request.state) R.string.cancel_request else R.string.apply
                                        ),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButton(
                    text = stringResource(R.string.close),
                    onClick = onDismiss,
                    width = 140.dp
                )
            }
        }
    )
}