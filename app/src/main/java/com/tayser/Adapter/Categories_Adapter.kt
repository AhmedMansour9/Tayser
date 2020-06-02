package com.tayser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tayser.Model.Sections_Response
import com.tayser.R
import com.tayser.View.ProductBytUd_View

class Categories_Adapter (context: Context, val userList: List<Sections_Response.Data>)
    : RecyclerView.Adapter<Categories_Adapter.ViewHolder>() {
    lateinit var productbyid: ProductBytUd_View
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Categories_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_category, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Categories_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.setOnClickListener(){
           this.productbyid.Id(userList.get(position))
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }
    fun onClick(productI:ProductBytUd_View){
        this.productbyid=productI
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context

        fun bindItems(dataModel: Sections_Response.Data) {
            val img = itemView.findViewById(R.id.imag_product) as ImageView
            val T_Name = itemView.findViewById(R.id.T_Name) as TextView

            T_Name.text=dataModel.title
            Glide.with(context)
                .load("http://atcs-egy.com" + dataModel.image)
                .into(img)
        }
    }
}