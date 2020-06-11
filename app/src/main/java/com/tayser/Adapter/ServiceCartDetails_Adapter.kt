package com.tayser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tayser.Model.CartServiceDetails_Response
import com.tayser.Model.CartService_Response
import com.tayser.R
import com.tayser.View.DeleteService_View
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_service.view.*
import kotlinx.android.synthetic.main.row_service.view.T_Price

class ServiceCartDetails_Adapter (context: Context, val userList: List<CartServiceDetails_Response.Data.Details>)
    : RecyclerView.Adapter<ServiceCartDetails_Adapter.ViewHolder>() {
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
    ): ServiceCartDetails_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_cartdetailsservice, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ServiceCartDetails_Adapter.ViewHolder, position: Int) {
        holder.itemView.T_Title.text=userList.get(position).name
        holder.itemView.T_Price.text=userList.get(position).price+" "+ context.getString(
            R.string.currency)


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
