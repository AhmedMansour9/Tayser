package com.tayser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.chicchicken.Model.Checkbox_Id
import com.chicchicken.Model.Checkboxs
import com.tayser.Model.Services_Response
import com.tayser.R
import kotlinx.android.synthetic.main.row_service.view.*
import java.util.ArrayList

class Services_Adapter (context: Context, val userList: List<Services_Response.Data>)
    : RecyclerView.Adapter<Services_Adapter.ViewHolder>() {
//    lateinit var productbyid: onClickFilter_View
//    lateinit var checkbox: ChechBox_View

    var row_index:Int = -1
    var context: Context =context
    companion object {
        var IdList: MutableList<Checkbox_Id> = ArrayList()
    }
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): Services_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_service, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: Services_Adapter.ViewHolder, position: Int) {
        holder.itemView.T_Title.text=userList.get(position).title
        holder.itemView.T_Price.text=userList.get(position).price+" "+ context.getString(R.string.currency)


        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val puthesData = Checkboxs()
                puthesData.id=userList.get(position).id!!
                if (IdList.isEmpty()) {
                    val checkbox_id = Checkbox_Id()
                    checkbox_id.setId(userList.get(position).id)
                    checkbox_id.setTitle(userList.get(position).title)

                    IdList.add(checkbox_id)

                } else {
                    val poistion = userList.get(position).id
                    for (i in IdList.indices) {
                        if (IdList.get(i).getId() === poistion) {
                            IdList.removeAt(i)
                            break
                        }
                    }

                    val checkbox_id = Checkbox_Id()
                    checkbox_id.setTitle(userList.get(position).title)
                    checkbox_id.setId(userList.get(position).id)
                    IdList.add(checkbox_id)
                }
            } else {
                val poistio = userList.get(position).id
                for (i in IdList.indices) {
                    if (IdList.get(i).getId() === poistio) {
                        IdList.removeAt(i)
                      break
                    }
                }
            }

        }


    }
//    fun onClickCheck(onClickProductColorView: ChechBox_View) {
//        this.checkbox = onClickProductColorView
//    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }


    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        private val context: Context = itemView.context
        var checkBox = itemView.findViewById(R.id.checkBox) as CheckBox

    }
}