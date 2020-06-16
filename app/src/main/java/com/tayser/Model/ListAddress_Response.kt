package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ListAddress_Response(
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
        @SerializedName("activate")
        var activate: String?,
        @SerializedName("address")
        var address: String?,
        @SerializedName("area")
        var area: String?,
        @SerializedName("area_price")
        var area_price: String?,
        @SerializedName("city")
        var city: String?,
        @SerializedName("country")
        var country: String?,
        @SerializedName("customer_name")
        var customerName: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("notes")
        var notes: String?,
        @SerializedName("number_flat")
        var numberFlat: String?,
        @SerializedName("number_floor")
        var numberFloor: String?,
        @SerializedName("number_house")
        var numberHouse: String?,
        @SerializedName("phone")
        var phone: String?
    ) : Parcelable
}