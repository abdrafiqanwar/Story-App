package com.example.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.data.response.ListStoryItem
import com.example.app.databinding.ItemStoryBinding

class StoryAdapter(private val listStory: List<ListStoryItem>): RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (photoUrl, name, description) = listStory[position]
        Glide.with(holder.itemView.context).load(photoUrl).into(holder.binding.ivImage)
        holder.binding.tvName.text = name
        holder.binding.tvDesc.text = description
    }
}