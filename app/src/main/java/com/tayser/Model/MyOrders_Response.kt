package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class MyOrders_Response(
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
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("customer_address")
        val customerAddress: String,
        @SerializedName("customer_city")
        val customerCity: String,
        @SerializedName("customer_comments_extra")
        val customerCommentsExtra: String,
        @SerializedName("customer_country")
        val customerCountry: String,
        @SerializedName("customer_phone")
        val customerPhone: String,
        @SerializedName("customer_street")
        val customerStreet: String,
        @SerializedName("delevery_fees")
        val deleveryFees: String,
        @SerializedName("langtude")
        val langtude: String,
        @SerializedName("lattude")
        val lattude: String,
        @SerializedName("order_id")
        val orderId: Int,
        @SerializedName("order_stat")
        val orderStat: String,
        @SerializedName("order_total_price")
        val orderTotalPrice: String,
        @SerializedName("payment_method")
        val paymentMethod: String,
        @SerializedName("payment_status")
        val paymentStatus: String,
        @SerializedName("tax")
        val tax: Double
    ) : Parcelable
}