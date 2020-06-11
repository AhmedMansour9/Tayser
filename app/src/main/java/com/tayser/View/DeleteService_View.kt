package com.tayser.View

import com.tayser.Model.CartService_Response

interface DeleteService_View {
    fun delete(Id:String)
    fun details(details: CartService_Response.Data.Details)
}