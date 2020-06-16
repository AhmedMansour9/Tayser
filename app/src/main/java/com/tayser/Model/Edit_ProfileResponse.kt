package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Edit_ProfileResponse(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
) : Parcelable