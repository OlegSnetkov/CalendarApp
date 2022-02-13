package com.avtograv.calendarapp.ui.calendarFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avtograv.calendarapp.databinding.EventRowBinding
import com.avtograv.calendarapp.model.EventModelData
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class AdapterRecyclerView(private val onClickListener: OnClickListener) :
    ListAdapter<EventModelData, AdapterRecyclerView.EventHolder>(AsyncDifferConfig.Builder(
        DiffCallback()).build()) {

    inner class EventHolder(private val eventRowBinding: EventRowBinding) :
        RecyclerView.ViewHolder(eventRowBinding.root) {

        fun bind(calendarEvent: EventModelData?) {
            eventRowBinding.tvEventName.text = calendarEvent?.name
            eventRowBinding.tvEventDescription.text = calendarEvent?.description
            eventRowBinding.tvBeginEventTime.text =
                SimpleDateFormat("h:mm", Locale.US).format(Date(calendarEvent!!.dateStart))
            eventRowBinding.tvFinishEventTime.text =
                SimpleDateFormat("h:mm", Locale.US).format(Date(calendarEvent.dateFinish))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        return EventHolder(EventRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(event)
        }
    }

    class OnClickListener(val clickListener: (eventModelData: EventModelData) -> Unit) {
        fun onClick(eventModelData: EventModelData) = clickListener(eventModelData)
    }
}

private class DiffCallback : DiffUtil.ItemCallback<EventModelData>() {
    override fun areItemsTheSame(oldItem: EventModelData, newItem: EventModelData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: EventModelData, newItem: EventModelData): Boolean {
        return oldItem.id == newItem.id
    }
}