package com.avtograv.calendarapp.repositories

import com.avtograv.calendarapp.model.EventModelData

sealed class EventDataStatus{
    object Loading : EventDataStatus()
    object Added : EventDataStatus()
    object Deleted : EventDataStatus()
    data class Result(val petList: List<EventModelData>) : EventDataStatus()
}
