package com.avtograv.calendarapp.ui.getEvents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avtograv.calendarapp.databinding.ItemEventBinding
import com.avtograv.calendarapp.model.EventModel


class Adapter(private val onClickListener: OnClickListener) :
    ListAdapter<EventModel, Adapter.EventViewHolder>(
        AsyncDifferConfig.Builder(DiffCallback()).build()) {

    private val events = mutableListOf<EventModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    inner class EventViewHolder(private val itemEventBinding: ItemEventBinding) :
        RecyclerView.ViewHolder(itemEventBinding.root) {
        fun bind(event: EventModel?) {
            itemEventBinding.tvEventName.text = event?.name
            itemEventBinding.tvEventDescription.text = event?.description
            itemEventBinding.tvBeginEventTime.text = event!!.dateStart
            itemEventBinding.tvFinishEventTime.text = event.dateFinish
        }
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(event)
        }
    }

    class OnClickListener(val clickListener: (event: EventModel) -> Unit) {
        fun onClick(event: EventModel) = clickListener(event)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(eventList: List<EventModel>) {
        events.clear()
        events.addAll(eventList)
        notifyDataSetChanged()
    }
}

private class DiffCallback : DiffUtil.ItemCallback<EventModel>() {
    override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
        return oldItem.id == newItem.id
    }
}