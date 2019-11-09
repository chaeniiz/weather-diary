package com.chaeniiz.weatherdiary.presentation.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.R
import org.jetbrains.anko.onClick

class DiaryRecyclerAdapter(
    private val diaries: List<Diary>,
    private val onItemClicked: (id: Int) -> Unit
) : RecyclerView.Adapter<DiaryViewHolder>() {

    override fun getItemCount(): Int = diaries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder =
        DiaryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        ).apply {
            itemView.onClick {
                onItemClicked(diaries[adapterPosition].id)
            }
        }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.bind(diaries[position])
    }
}
