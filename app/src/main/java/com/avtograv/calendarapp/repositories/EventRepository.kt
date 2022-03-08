package com.avtograv.calendarapp.repositories

import androidx.lifecycle.MutableLiveData
import com.avtograv.calendarapp.model.EventModelData
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun addEvent(dateStart: Long, dateFinish: Long, name: String, description: String?): Flow<EventDataStatus>

    fun eventsOfDays(timestampThisDay: Long): MutableLiveData<List<EventModelData>>

    fun descriptionEvent(idEvent: String): MutableLiveData<EventModelData>
}