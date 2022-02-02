package com.avtograv.calendarapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avtograv.calendarapp.data.realw.EventRealmModel
import com.avtograv.calendarapp.databinding.EventRowBinding


class AdapterRecyclerView :
    ListAdapter<EventRealmModel, AdapterRecyclerView.EventHolder>(AsyncDifferConfig.Builder(
        DiffCallback()).build()) {

    inner class EventHolder(private val eventRowBinding: EventRowBinding) :
        RecyclerView.ViewHolder(eventRowBinding.root) {

        fun bind(eventModelData: EventRealmModel?) {
            eventRowBinding.tvEventName.text = eventModelData?.name
            eventRowBinding.tvEventDescription.text = eventModelData?.description
            eventRowBinding.tvBeginEventTime.text = eventModelData?.dateStart.toString()
            eventRowBinding.tvFinishEventTime.text = eventModelData?.dateFinish.toString()
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
    }
}

private class DiffCallback : DiffUtil.ItemCallback<EventRealmModel>() {
    override fun areItemsTheSame(oldItem: EventRealmModel, newItem: EventRealmModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: EventRealmModel, newItem: EventRealmModel): Boolean {
        return oldItem.id == newItem.id
    }
}