package com.tayser.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chicchicken.View.PlusId_View
import com.tayser.Model.Cart_Response
import com.tayser.R
import kotlinx.android.synthetic.main.row_cart.view.*

class Cart_Adapter (context: Context, val userList: List<Cart_Response.Data.X>)
    : RecyclerView.Adapter<Cart_Adapter.ViewHolder>() {
    companion object
    {
        lateinit var plusId_View: PlusId_View
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cart_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_cart, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Cart_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.img_delete.setOnClickListener(){
            Cart_Adapter.plusId_View.delete(userList.get(position).cartId.toString())
            userList.drop(position)
            notifyDataSetChanged()

        }
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

            itemView.T_Name.text = dataModel.productName
            itemView.T_Counter.text=dataModel.quantity
            itemView.T_Price.text=context.getString(R.string.price)+" "+dataModel.unitPrice+" "+context.getString(R.string.currency)
            itemView.T_TotalPrice.text=context.getString(R.string.total)+" "+dataModel.totalUnitPrice.toString()+" "+context.getString(R.string.currency)

            val requestOptions = RequestOptions()
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .skipMemoryCache(true)
                .placeholder(R.drawable.place_holder)
//                .priority(Priority.IMMEDIATE)
//                .encodeFormat(Bitmap.CompressFormat.PNG)
//                .format(DecodeFormat.DEFAULT)


            Glide.with(context)
                .load("http://atcs-egy.com" + dataModel.image)
                .apply(requestOptions)
                .into(itemView.Img_Product)

            itemView.T_Plus.setOnClickListener(){
               var count :Int= (itemView.T_Counter.text as String).toInt()
               count++
               itemView.T_Counter.text=count.toString()
               Cart_Adapter.plusId_View.Plus_Id(dataModel.cartId.toString())
           }
            itemView.T_Minus.setOnClickListener(){
                var count = Integer.parseInt(itemView.T_Counter.text.toString())
                if(count>1) {
                    count--
                    itemView.T_Counter.text=count.toString()
                    Cart_Adapter.plusId_View.minus_Id(dataModel.cartId.toString())
                }
            }


        }
    }
    }
