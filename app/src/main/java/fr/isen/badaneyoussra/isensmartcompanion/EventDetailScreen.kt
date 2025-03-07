package fr.isen.badaneyoussra.isensmartcompanion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import androidx.compose.ui.unit.sp

@Composable
fun EventDetailScreen(event: Event?) {
    if (event == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Aucun événement trouvé", fontSize = 20.sp, color = Color.Red)
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = event.title, fontSize = 24.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Date: ${event.date}", fontSize = 18.sp, color = Color.Gray)
                Text(text = "Lieu: ${event.location}", fontSize = 18.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = event.description, fontSize = 16.sp)
            }
        }
    }
}
