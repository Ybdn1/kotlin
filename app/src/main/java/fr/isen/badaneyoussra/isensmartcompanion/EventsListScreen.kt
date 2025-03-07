package fr.isen.badaneyoussra.isensmartcompanion

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EventsListScreen(navController: NavController) {
    var eventsList by remember { mutableStateOf<List<Event>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Charger les événements depuis Retrofit
    LaunchedEffect(Unit) {
        val call = RetrofitClient.api.getEvents()
        call.enqueue(object : Callback<Map<String, Event>> {
            override fun onResponse(call: Call<Map<String, Event>>, response: Response<Map<String, Event>>) {
                if (response.isSuccessful) {
                    // Log pour vérifier la réponse brute
                    Log.d("Events", "Response body: ${response.body()}")
                    eventsList = response.body()?.values?.toList() ?: emptyList()
                }
                isLoading = false
            }

            override fun onFailure(call: Call<Map<String, Event>>, t: Throwable) {
                isLoading = false
                // Log pour vérifier l'échec de la requête
                Log.e("Events", "Error fetching events", t)
            }
        })
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn {
            items(eventsList) { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("eventDetail/${event.id}") },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = event.title, fontSize = 20.sp)
                        Text(text = event.date, color = Color.Gray)
                        Text(text = event.location, color = Color.DarkGray)
                    }
                }
            }
        }
    }
}
