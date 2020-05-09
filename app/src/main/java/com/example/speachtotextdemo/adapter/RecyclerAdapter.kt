package com.example.speachtotextdemo.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.speachtotextdemo.R
import com.example.speachtotextdemo.responce.Dictionary
import kotlinx.android.synthetic.main.item_recycler.view.*

class RecyclerAdapter(var context: Context, var list: List<Dictionary>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.item_recycler, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.word.text = list.get(position).word
        holder.frequency.text = list.get(position).frequency.toString()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {
        /** update the view when adapter is notified*/
        if (!payloads.isEmpty()) {
            for (pay in payloads) {
                if (pay.equals("payload")) {
                    holder.rootLayout.setBackgroundColor(Color.parseColor("#CEC9C8"))
                    holder.word.setTextColor(Color.parseColor("#000000"))
                    holder.frequency.setTextColor(Color.parseColor("#000000"))

                } else {
                    holder.rootLayout.setBackgroundColor(Color.parseColor("#ffffff"))

                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val word = view.tv_name
        val frequency = view.tv_count
        val rootLayout = view.ll_root
    }

}