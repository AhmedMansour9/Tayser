package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Cart_Response(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("list")
        val list: List<X>,
        @SerializedName("price")
        val price: Double,
        @SerializedName("total_delevery_fees")
        val totalDeleveryFees: String,
        @SerializedName("total_tax")
        val totalTax: Double
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class X(
            @SerializedName("cart_id")
            val cartId: Int,
            @SerializedName("currency")
            val currency: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("image")
            val image: String,
            @SerializedName("product_id")
            val productId: String,
            @SerializedName("product_name")
            val productName: String,
            @SerializedName("quantity")
            val quantity: String,
            @SerializedName("short_description")
            val shortDescription: String,
            @SerializedName("total_unit_price")
            val totalUnitPrice: Int,
            @SerializedName("unit_price")
            val unitPrice: String
        ) : Parcelable
    }
}