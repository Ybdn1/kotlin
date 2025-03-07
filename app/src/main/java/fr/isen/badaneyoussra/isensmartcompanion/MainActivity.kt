package fr.isen.badaneyoussra.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import fr.isen.badaneyoussra.isensmartcompanion.ui.theme.MyApplicationTheme
import androidx.compose.foundation.lazy.LazyColumn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var eventsList by remember { mutableStateOf<List<Event>>(emptyList()) }
    var userInput by remember { mutableStateOf(TextFieldValue()) }

    // Charger les événements depuis Retrofit
    LaunchedEffect(Unit) {
        val call = RetrofitClient.api.getEvents()
        call.enqueue(object : Callback<Map<String, Event>> {
            override fun onResponse(call: Call<Map<String, Event>>, response: Response<Map<String, Event>>) {
                if (response.isSuccessful) {
                    eventsList = response.body()?.values?.toList() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<Map<String, Event>>, t: Throwable) {
                // Gérer l'erreur ici
            }
        })
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F6FC))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.isen),
                contentDescription = "Logo ISEN",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "ISEN",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFC62828)
            )
            Text(
                text = "Smart Companion",
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEDEBF2), shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFC62828)),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { /* Action à définir */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Envoyer",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable("home") { HomeScreen() }
                composable("events") { EventsScreen(navController) }
                composable("history") { HistoryScreen() }
                composable("eventsList") { EventsListScreen(navController) }
                composable("eventDetail/{eventId}") { backStackEntry ->
                    val eventId = backStackEntry.arguments?.getString("eventId")?.toIntOrNull()
                    val event = eventsList.find { it.id == eventId }

                    if (event != null) {
                        EventDetailScreen(event)
                    } else {
                        Text("Event not found", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        "home" to Icons.Default.Home,
        "events" to Icons.Default.Favorite,
        "history" to Icons.Default.Settings
    )

    NavigationBar {
        val currentRoute = navController.currentDestination?.route

        items.forEach { (route, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = route) },
                label = { Text(route.replaceFirstChar { it.uppercase() }) },
                selected = currentRoute == route,
                onClick = { navController.navigate(route) }
            )
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to Home Screen", fontSize = 24.sp)
    }
}

@Composable
fun EventsScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Events Screen", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate("eventsList")
        }) {
            Text("View Events")
        }
    }
}

@Composable
fun HistoryScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("History Screen", fontSize = 24.sp)
    }
}


// Navigation setup
@OptIn(ExperimentalMaterial3Api::class)

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}
