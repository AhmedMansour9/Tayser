package com.tayser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tayser.Model.CartService_Response
import com.tayser.Model.OrderListService_Response
import com.tayser.R
import com.tayser.View.DeleteService_View
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_service.view.*
import kotlinx.android.synthetic.main.row_service.view.T_Price

class OrderListService_Adapter (context: Context, val userList: List<OrderListService_Response.Data>)
    : RecyclerView.Adapter<OrderListService_Adapter.ViewHolder>() {
//    lateinit var productbyid: onClickFilter_View
//    lateinit var checkbox: ChechBox_View

    var row_index:Int = -1
    var context: Context =context
    companion object {
        lateinit var plusId_View: DeleteService_View
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderListService_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_orderservice, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: OrderListService_Adapter.ViewHolder, position: Int) {
        holder.itemView.T_Title.text=userList.get(position).sectionName
        holder.itemView.T_Price.text=userList.get(position).totalServiceQuantity+" "+ context.getString(
            R.string.currency)

//        holder.itemView.img_delete.setOnClickListener(){
//            plusId_View.delete(userList.get(position).cartServiceId.toString())
//            userList.drop(position)
//            notifyDataSetChanged()
//
//        }

//        holder.itemView.setOnClickListener(){
//            plusId_View.details(userList.get(position))
//            userList.drop(position)
//            notifyDataSetChanged()
//
//        }
    }
    fun OnClickDelete(plusId_Vie: DeleteService_View) {

        plusId_View =plusId_Vie
    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        private val context: Context = itemView.context

    }
}
