package com.avtograv.calendarapp.repository

import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun addEvent(dateStart: Long, dateFinish: Long, name: String, description: String?): Flow<EventDataStatus>

    fun eventsOfDays(timestampThisDay: Long): Flow<EventDataStatus>

    fun descriptionEvent(idEvent: String): Flow<EventDataStatus>
}