package com.tayser.Model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Register_Model(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
) {
    @Keep
    data class Data(
        @SerializedName("email")
        val email: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("role_id")
        val roleId: Int,
        @SerializedName("role_name")
        val roleName: String,
        @SerializedName("user_token ")
        val userToken: String
    )
}