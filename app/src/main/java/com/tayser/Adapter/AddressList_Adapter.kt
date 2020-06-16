package com.tayser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tayser.Model.CartService_Response
import com.tayser.Model.ListAddress_Response
import com.tayser.R
import com.tayser.View.Address_View
import com.tayser.View.DeleteService_View
import kotlinx.android.synthetic.main.row_address.view.*
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_service.view.*
import kotlinx.android.synthetic.main.row_service.view.T_Price

class AddressList_Adapter  (context: Context, val userList: List<ListAddress_Response.Data>)
    : RecyclerView.Adapter<AddressList_Adapter.ViewHolder>() {
//    lateinit var productbyid: onClickFilter_View
//    lateinit var checkbox: ChechBox_View

    var row_index:Int = -1
    var context: Context =context
    companion object {
        lateinit var plusId_View: Address_View
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressList_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_address, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: AddressList_Adapter.ViewHolder, position: Int) {
        holder.itemView.T_country.text=userList.get(position).country
        holder.itemView.T_City.text=userList.get(position).city
        holder.itemView.T_Area.text=userList.get(position).area
        holder.itemView.T_Address.text=userList.get(position).address
        holder.itemView.T_name.text=userList.get(position).customerName
        holder.itemView.T_phone.text=userList.get(position).phone
        if(userList.get(position).activate.equals("1")){
            holder.itemView.T_Active.text=context.resources.getString(R.string.activited)

        }else {
            holder.itemView.T_Active.text=context.resources.getString(R.string.unactivited)

        }

        holder.itemView.setOnClickListener(){
            plusId_View.address(userList.get(position))
            userList.drop(position)
            notifyDataSetChanged()

        }
    }
    fun OnClick(plusId_Vie: Address_View) {

        plusId_View =plusId_Vie
    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
