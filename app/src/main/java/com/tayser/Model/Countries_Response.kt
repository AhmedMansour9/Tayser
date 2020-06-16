package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Countries_Response(
    @SerializedName("data")
    var `data`: List<Data?>?,
    @SerializedName("error")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("id")
        var id: Int?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("price")
        var price: String?

    ) : Parcelable
}