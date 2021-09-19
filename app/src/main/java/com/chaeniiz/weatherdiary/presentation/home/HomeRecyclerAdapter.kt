package com.chaeniiz.weatherdiary.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.R

class HomeRecyclerAdapter(
    private val diaries: List<Diary>,
    private val onItemClicked: (id: Int) -> Unit
) : RecyclerView.Adapter<HomeViewHolder>() {

    override fun getItemCount(): Int = diaries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        ).apply {
            itemView.setOnClickListener {
                onItemClicked(diaries[bindingAdapterPosition].id)
            }
        }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(diaries[position])
    }
}
