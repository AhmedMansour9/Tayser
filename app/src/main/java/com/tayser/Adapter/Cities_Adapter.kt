package com.tayser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.tayser.Model.Countries_Response
import com.tayser.R


class Cities_Adapter(
    val applicationContext: Context,
   val data: List<Countries_Response.Data>
) : BaseAdapter() {
    lateinit var list: List<Countries_Response.Data>
    lateinit var inflter: LayoutInflater
    init {
        this.inflter = LayoutInflater.from(applicationContext)
    }

    override fun getCount(): Int {

        return  data.size

    }

    override fun getItem(position: Int): String? {
        return data.get(position).title
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.inflter.inflate(R.layout.custom_spinner_items, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        vh.label.text = data.get(position).title
        return view
    }
}

private class ListRowHolder(row: View?) {
    public val label: TextView

    init {
        this.label = row?.findViewById(R.id.T_City) as TextView
    }
}