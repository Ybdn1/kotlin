package fr.isen.badaneyoussra.isensmartcompanion
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName


data class Event(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("date") val date: String,
    @SerializedName("location") val location: String,
    @SerializedName("category") val category: String
)
