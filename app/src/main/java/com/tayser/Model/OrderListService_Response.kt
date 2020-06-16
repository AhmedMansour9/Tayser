package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class OrderListService_Response(
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
        @SerializedName("currency")
        var currency: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("section_description")
        var sectionDescription: String?,
        @SerializedName("section_name")
        var sectionName: String?,
        @SerializedName("total_service_quantity")
        var totalServiceQuantity: String?
    ) : Parcelable
}