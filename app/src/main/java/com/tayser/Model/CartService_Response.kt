package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CartService_Response(
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
        data class  Details(
            @SerializedName("cart_service_id")
            var cartServiceId: Int?,
            @SerializedName("currency")
            var currency: String?,
            @SerializedName("description")
            var description: String?,
            @SerializedName("name")
            var name: String?,
            @SerializedName("section_id")
            var sectionId: String?,
            @SerializedName("short_description")
            var shortDescription: String?,
            @SerializedName("total_service_quantity")
            var totalServiceQuantity: String?
        ) : Parcelable
    }
}