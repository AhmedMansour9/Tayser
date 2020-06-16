package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class OrderProduct_Response(
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
        @SerializedName("product_name")
        var productName: String?,
        @SerializedName("product_price")
        var productPrice: String?,
        @SerializedName("product_quantity")
        var productQuantity: String?
    ) : Parcelable
}