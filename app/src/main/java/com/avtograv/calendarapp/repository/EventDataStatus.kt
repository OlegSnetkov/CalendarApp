package com.avtograv.calendarapp.repository

import com.avtograv.calendarapp.model.EventModel

sealed class EventDataStatus{
    object Loading : EventDataStatus()
    object Added : EventDataStatus()
    data class ResultList(val eventList: List<EventModel>) : EventDataStatus()
    data class Result(val event: EventModel) : EventDataStatus()
}