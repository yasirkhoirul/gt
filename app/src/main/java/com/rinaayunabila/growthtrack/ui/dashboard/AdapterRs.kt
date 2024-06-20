package com.rinaayunabila.growthtrack.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rinaayunabila.growthtrack.R
import com.rinaayunabila.growthtrack.data.Hospital

class HospitalAdapter(private val hospitals: List<Hospital>) :
    RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {

    class HospitalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHospitalName: TextView = itemView.findViewById(R.id.namarumahakit)
        val tvDistance: TextView = itemView.findViewById(R.id.jarak)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rumahsakit, parent, false)
        return HospitalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val hospital = hospitals[position]
        holder.tvHospitalName.text = hospital.name
        holder.tvDistance.text = String.format("%.2f meters", hospital.distance)
    }

    override fun getItemCount() = hospitals.size
}