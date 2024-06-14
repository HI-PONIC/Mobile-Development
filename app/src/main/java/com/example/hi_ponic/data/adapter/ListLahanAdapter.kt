package com.example.hi_ponic.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hi_ponic.R
import com.example.hi_ponic.data.response.PlantsItem

class ListLahanAdapter(
    private val TanamanList: List<PlantsItem>,
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<ListLahanAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.Plant)
        val dateadded: TextView = itemView.findViewById(R.id.TanggalTanam)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = TanamanList[position]
        holder.name.text = item.name
        holder.dateadded.text = item.dateAdded
        Glide.with(holder.itemView.context)
            .load(item.image)
            .error(R.drawable.ic_launcher_background)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return TanamanList.size
    }

    interface OnAdapterListener {
        fun onClick(story: PlantsItem)
    }
}