package com.chaeniiz.weatherdiary.presentation.diary

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.presentation.includeCommaAndSpace
import com.chaeniiz.weatherdiary.presentation.toFormattedString
import kotlinx.android.synthetic.main.item_diary.view.*

class DiaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(diary: Diary) {
        with(itemView) {
            dateTextView.text = diary.updatedAt.toFormattedString("yyyy. MM. dd")
            locationTextView.text = diary.location.includeCommaAndSpace()
            weatherTextView.text = diary.weather
            contentTextView.text = diary.content
        }
    }
}
