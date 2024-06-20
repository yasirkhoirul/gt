package com.rinaayunabila.growthtrack.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rinaayunabila.growthtrack.R
import com.rinaayunabila.growthtrack.data.DataaItem
import de.hdodenhof.circleimageview.CircleImageView

interface OnItemClickListener {
    fun onItemClick(dataItem: DataaItem)
}


class AdapterHistory(
    private val dataList: List<DataaItem?>?,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<AdapterHistory.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.namabayi)
        val gambar: CircleImageView = itemView.findViewById(R.id.gambarbayi)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val dataItem = dataList?.get(position)
                    dataItem?.let { listener.onItemClick(it) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = dataList?.get(position)
        if (dataItem != null) {
            holder.textView.text = dataItem.babyName
            val gender = dataItem.gender
            if (gender == 1) {
                holder.gambar.setImageResource(R.drawable.boy)
            } else {
                holder.gambar.setImageResource(R.drawable.girl)
            }
        }
    }

    override fun getItemCount() = dataList?.size ?: 0
}
