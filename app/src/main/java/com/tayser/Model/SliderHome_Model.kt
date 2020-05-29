package com.tayser.Model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SliderHome_Model(
    @SerializedName("data")
    val `data`: List<Slider_Home>,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
)
{
    @Keep
    data class Slider_Home(
        @SerializedName("description")
        val description: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("title")
        val title: String
    )
}