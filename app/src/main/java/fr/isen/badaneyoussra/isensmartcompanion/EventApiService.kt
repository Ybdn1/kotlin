package fr.isen.badaneyoussra.isensmartcompanion

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

interface EventApiService {
    @GET("events.json")  // L'endpoint de l'API
    fun getEvents(): Call<Map<String, Event>> // Retourne une Map<String, Event>
}
