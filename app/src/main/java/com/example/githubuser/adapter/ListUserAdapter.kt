package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.response.SearchResponse

class ListUserAdapter(private val listUser: ArrayList<SearchResponse.ItemsItem>)
    : RecyclerView.Adapter<ListUserAdapter.ViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int)
    : ViewHolder{
        val binding = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val list = listUser[position]
        viewHolder.binding.tvName.text = list.login
        Glide.with(viewHolder.binding.imgPhoto)
            .load(list.avatarUrl)
            .circleCrop()
            .into(viewHolder.binding.imgPhoto)

        viewHolder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<SearchResponse.ItemsItem>){
        listUser.clear()
        listUser.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(items: SearchResponse.ItemsItem)
    }

}