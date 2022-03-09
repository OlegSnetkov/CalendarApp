package com.avtograv.calendarapp.repository

import com.avtograv.calendarapp.model.EventModel

sealed class EventDataStatus{
    object Loading : EventDataStatus()
    object Added : EventDataStatus()
    object Deleted : EventDataStatus()
    data class Result(val eventList: List<EventModel>) : EventDataStatus()
}