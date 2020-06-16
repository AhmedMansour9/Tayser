package com.tayser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chicchicken.View.PlusId_View
import com.tayser.Model.CartService_Response
import com.tayser.Model.Cart_Response
import com.tayser.R
import kotlinx.android.synthetic.main.row_cartorder.view.*

class CartServiceOrder_Adapter (context: Context, val userList: List<CartService_Response.Data.Details>)
    : RecyclerView.Adapter<CartServiceOrder_Adapter.ViewHolder>() {
    companion object
    {
        lateinit var plusId_View: PlusId_View
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartServiceOrder_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_cartorder, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CartServiceOrder_Adapter.ViewHolder, position: Int) {
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

        fun bindItems(dataModel: CartService_Response.Data.Details) {

            itemView.T_Title.text = dataModel.name

            itemView.T_Price.text=dataModel.totalServiceQuantity.toString()+" "+context.getString(
                R.string.currency)


        }
    }
}
