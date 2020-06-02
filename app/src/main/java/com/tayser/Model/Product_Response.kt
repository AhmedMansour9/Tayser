package com.tayser.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import androidx.annotation.Keep

@SuppressLint("ParcelCreator")
@Parcelize
@Keep
data class Product_Response(
    @SerializedName("data") var `data`: List<Data?>?,
    @SerializedName("error") var error: String?,
    @SerializedName("status") var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    @Keep
    data class Data(
        @SerializedName("description") var description: String?,
        @SerializedName("id") var id: Int?,
        @SerializedName("image") var image: String?,
        @SerializedName("price") var price: String?,
        @SerializedName("short_description") var shortDescription: String?,
        @SerializedName("title") var title: String?,
        @SerializedName("type") var type: String?,
        @SerializedName("vendor_name") var vendorName: String?
    ) : Parcelable
}