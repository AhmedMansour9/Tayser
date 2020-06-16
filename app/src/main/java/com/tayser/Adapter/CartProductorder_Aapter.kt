package com.tayser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chicchicken.View.PlusId_View
import com.tayser.Model.Cart_Response
import com.tayser.R
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_cart.view.T_Price
import kotlinx.android.synthetic.main.row_cartorder.view.*

class CartProductorder_Aapter (context: Context, val userList: List<Cart_Response.Data.X>)
    : RecyclerView.Adapter<CartProductorder_Aapter.ViewHolder>() {
    companion object
    {
        lateinit var plusId_View: PlusId_View
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductorder_Aapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_cartorder, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CartProductorder_Aapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }
    fun OnClickPlus(plusId_Vie: PlusId_View) {

        plusId_View=plusId_Vie
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context =itemView.context

        fun bindItems(dataModel: Cart_Response.Data.X) {

            itemView.T_Title.text = dataModel.productName

            itemView.T_Price.text=dataModel.totalUnitPrice.toString()+" "+context.getString(
                R.string.currency)


        }
    }
}
