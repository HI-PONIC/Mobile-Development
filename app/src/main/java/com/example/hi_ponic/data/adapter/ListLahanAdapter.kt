package com.example.hi_ponic.data.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//class ListLahanAdapter : ListAdapter<...., ListLahanAdapter.MyViewHolder>(DIFF_CALLBACK) {
//    private lateinit var onItemClickCallback: OnItemClickCallback
//
//    interface OnItemClickCallback {
//        fun OnItemCLicked(data: ListStoryItem)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(binding)
//    }
//
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val story = getItem(position)
//        holder.bind(story)
//        holder.itemView.setOnClickListener {
//            onItemClickCallback.OnItemCLicked(story!!)
//
//            val intent = Intent(holder.itemView.context, DetailStoryActivity::class.java)
//
//            // Tambahkan data tambahan ke intent
//            intent.putExtra(DetailStoryActivity.EXTRA_ID, story.id)
//            intent.putExtra(DetailStoryActivity.EXTRA_NAME, story.name)
//            intent.putExtra(DetailStoryActivity.EXTRA_DESCRIPTION, story.description)
//            intent.putExtra(DetailStoryActivity.EXTRA_PHOTOURL, story.photoUrl)
//
//            val optionsCompat: ActivityOptionsCompat =
//                ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    holder.itemView.context as Activity,
//                    Pair(holder.itemView.findViewById(R.id.imageView2), "photoTransition")
//                )
//            holder.itemView.context.startActivity(intent, optionsCompat.toBundle())
//        }
//    }
//
//    class MyViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(story: ListStoryItem?) {
//            Glide.with(binding.root).load(story?.photoUrl).into(binding.imageView2)
//            binding.storyName.text = story?.name
//            binding.storyDescription.text = story?.description
//        }
//
//
//    }
//    companion object {
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
//            override fun areItemsTheSame(
//                oldItem: ListStoryItem,
//                newItem: ListStoryItem
//            ): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(
//                oldItem: ListStoryItem,
//                newItem: ListStoryItem
//            ): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }

//}