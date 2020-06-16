package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class About_Response(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("description")
        val description: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("title")
        val title: String
    ) : Parcelable
}