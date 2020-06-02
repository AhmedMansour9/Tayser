package com.tayser.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.tayser.Fragments.Products
import com.tayser.Model.Product_Response
import com.tayser.R
import com.tayser.View.ProductDetails_View
import kotlinx.android.synthetic.main.row_product.view.*

class Products_Adapter (context: Context, val userList: List<Product_Response.Data>)
    : RecyclerView.Adapter<Products_Adapter.ViewHolder>() {
//    lateinit var detailsProduct_id: DetailsProduct_id
    lateinit var ProductsDetails: ProductDetails_View
    var con=context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Products_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_product, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: Products_Adapter.ViewHolder, position: Int) {
        holder.bindItems(userList.get(position))
        holder.itemView.setOnClickListener(){
            ProductsDetails.details(userList.get(position))
        }




    }

    fun onClick(ProductsDetail: ProductDetails_View){
        this.ProductsDetails=ProductsDetail
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context =itemView.context
        val Img_Product = itemView.findViewById(R.id.Img_Product) as ImageView

        fun bindItems(dataModel: Product_Response.Data) {
            val title = itemView.findViewById(R.id.T_Name) as TextView
            val T_Price = itemView.findViewById(R.id.T_Price) as TextView

            val img = itemView.findViewById(R.id.Img_Product) as ImageView
            var T_Descrption=itemView.findViewById(R.id.T_Description)as TextView
            title.text = dataModel.title
            T_Descrption.text=dataModel.shortDescription
            T_Price.text=dataModel.price+" "+context.getString(R.string.currency)


            val requestOptions = RequestOptions()
                .placeholder(R.drawable.place_holder)


            Glide.with(context)
                .load("http://atcs-egy.com" + dataModel.image)
                .apply(requestOptions)
                .into(Img_Product)
        }
    }


}