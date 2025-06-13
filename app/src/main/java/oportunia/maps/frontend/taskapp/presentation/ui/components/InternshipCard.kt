package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationFlagDto


@Composable
fun InternshipCard(
    internshipFlag: InternshipLocationFlagDto,
    onRequestClick: (InternshipLocationFlagDto) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = internshipFlag.internship.details,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            //CustomButton(
                //text = "Request",
                //onClick = { onRequestClick(internshipFlag.internship) },
                //modifier = Modifier.width(200.dp)
            //)

            if (!internshipFlag.requested){
                CustomButton(stringResource(id = R.string.apply), onClick = { onRequestClick(internshipFlag) })
            }else{
                Row(
                    //horizontalAlignment = Alignment.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    androidx.compose.material.Text(
                        text = stringResource(id = R.string.requested),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    androidx.compose.material.Icon(
                        imageVector =Icons.Default.CheckCircle,
                        contentDescription = stringResource(id = R.string.requested),
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

        }

    }
}
