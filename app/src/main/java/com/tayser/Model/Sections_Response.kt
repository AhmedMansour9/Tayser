package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Sections_Response(
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
        @SerializedName("description")
        var description: String?,
        @SerializedName("have_emergency")
        var haveEmergency: String?,
        @SerializedName("have_maintenance")
        var haveMaintenance: String?,
        @SerializedName("have_product")
        var haveProduct: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("short_description")
        var shortDescription: String?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("type_section")
        var typeSection: String?
    ) : Parcelable
}