package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.NewData
import com.example.newsapp.databinding.ItemsBinding
import com.example.newsapp.helper.loadImage

class NewsAdapter :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val list = ArrayList<NewData>()

    inner class ViewHolder(private val binding:ItemsBinding ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NewData) {
            binding.apply {
                tvTitle.text = data.title
                tvSource.text = data.source.name
                tvDate.text =data.convertDate(data.publishedAt)
                data.urlToImage?.let { image.loadImage(it) }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        return ViewHolder(ItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        list.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = list.size


    fun AddAll(data : List<NewData>){
        list.addAll(data)
    }
}