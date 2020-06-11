package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CartServiceDetails_Response(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("error")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("count_services")
        var countServices: Int?,
        @SerializedName("currency")
        var currency: String?,
        @SerializedName("list")
        var list: List<Details>?,
        @SerializedName("total_services")
        var totalServices: String?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Details(
            @SerializedName("cart_id")
            var cartId: Int?,
            @SerializedName("currency")
            var currency: String?,
            @SerializedName("name")
            var name: String?,
            @SerializedName("price")
            var price: String?,
            @SerializedName("quantity")
            var quantity: String?,
            @SerializedName("total_price_quantity")
            var totalPriceQuantity: String?
        ) : Parcelable
    }
}