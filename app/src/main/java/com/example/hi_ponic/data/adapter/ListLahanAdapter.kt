package com.example.hi_ponic.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hi_ponic.data.adapter.ListLahanAdapter.MyViewHolder.Companion.DIFF_CALLBACK
import com.example.hi_ponic.data.response.PlantsItem
import com.example.hi_ponic.databinding.CardLayoutBinding
import com.example.hi_ponic.view.monitoring.DetailHydroponicStatisticActivity

class ListLahanAdapter : ListAdapter<PlantsItem, ListLahanAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun OnItemCLicked(data: PlantsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val plant = getItem(position)
        holder.bind(plant)
        holder.itemView.setOnClickListener {
            onItemClickCallback.OnItemCLicked(plant)

            val intent = Intent(holder.itemView.context, DetailHydroponicStatisticActivity::class.java)
        }
    }

    class MyViewHolder(private val binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plantsItem: PlantsItem) {
            binding.Plant.text = plantsItem.name
            binding.TanggalTanam.text = plantsItem.dateAdded
//            Glide.with(holder.itemView.context)
//                .load(item.image)
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.image)
        }

        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlantsItem>() {
                override fun areItemsTheSame(
                    oldItem: PlantsItem,
                    newItem: PlantsItem
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: PlantsItem,
                    newItem: PlantsItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }

}