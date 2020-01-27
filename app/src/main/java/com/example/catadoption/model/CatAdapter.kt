package com.example.catadoption.model

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.catadoption.R
import kotlinx.android.synthetic.main.cat_item.view.*

class CatAdapter(private val movies: List<Cat>, private val onClick: (Cat) -> Unit):
    RecyclerView.Adapter<CatAdapter.ViewHolder>() {


        private lateinit var context: Context

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.cat_item, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return movies.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(movies[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            init {
                itemView.setOnClickListener { onClick(movies[adapterPosition]) }
            }

            fun bind(cat: Cat) {
                itemView.tvDescription.text = cat.Description
                Glide.with(context).load(cat.Image_url).into(itemView.ivCat)
            }
        }
}