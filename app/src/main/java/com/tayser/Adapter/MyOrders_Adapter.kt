package com.tayser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chicchicken.View.PlusId_View
import com.tayser.Model.MyOrders_Response
import com.tayser.R
import com.tayser.View.DetailsMyOrdersView
import kotlinx.android.synthetic.main.item_myorder.view.*

class MyOrders_Adapter  ( val userList: List<MyOrders_Response.Data>)
    : RecyclerView.Adapter<MyOrders_Adapter.ViewHolder>() {
    lateinit var orderDetai: DetailsMyOrdersView

    companion object
    {
        lateinit var plusId_View: PlusId_View
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrders_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_myorder, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: MyOrders_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.setOnClickListener(){
            this.orderDetai.showDetailsMyOrders(userList.get(position))
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }
    fun OnClick(orderDetais: DetailsMyOrdersView) {
        orderDetai=orderDetais
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context =itemView.context

        fun bindItems(dataModel: MyOrders_Response.Data) {

            itemView.T_order_id.text= dataModel.orderId.toString()
            val orderStatusValue = dataModel.orderStat

            if (orderStatusValue == "0") {
                itemView.T_order_status.text=context.resources.getString(R.string.InProgress)
            } else if (orderStatusValue == "1") {
                itemView.T_order_status.text=context.resources.getString(R.string.Delivered)
            }
            else if (orderStatusValue == "2") {
                itemView.T_order_status.text=context.resources.getString(R.string.Canceled)
            }

            itemView.T_date.text=dataModel.createdAt
            itemView.T_price.text=dataModel.orderTotalPrice +context.resources.getString(R.string.currency)


        }
    }
}
