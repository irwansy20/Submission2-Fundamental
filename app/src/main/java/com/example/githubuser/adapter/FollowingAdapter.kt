package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.FollowingResponse
import com.example.githubuser.databinding.ItemUserBinding

class FollowingAdapter(private val listFollow: List<FollowingResponse.FollowingResponseItem>)
    : RecyclerView.Adapter<FollowingAdapter.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):
            ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = listFollow[position]
        holder.binding.tvName.text = list.login
        Glide.with(holder.binding.imgPhoto)
            .load(list.avatarUrl)
            .circleCrop()
            .into(holder.binding.imgPhoto)

    }

    override fun getItemCount(): Int = listFollow.size

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}