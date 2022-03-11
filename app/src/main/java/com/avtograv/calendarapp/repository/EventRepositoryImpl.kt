package com.avtograv.calendarapp.repository

import com.avtograv.calendarapp.realm.DatabaseOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EventRepositoryImpl(private val databaseOperations: DatabaseOperations) : EventRepository {

    override fun addEvent(
        dateStart: Long,
        dateFinish: Long,
        name: String,
        description: String?,
    ): Flow<EventDataStatus> = flow {
        emit(EventDataStatus.Loading)
        databaseOperations.insertEvent(dateStart, dateFinish, name, description)
        emit(EventDataStatus.Added)
    }.flowOn(Dispatchers.IO)

    override fun eventsOfDays(timestampThisDay: Long): Flow<EventDataStatus> = flow {
        emit(EventDataStatus.Loading)
        val eventsToday = databaseOperations.retrieveEvents(timestampThisDay)
        emit(EventDataStatus.ResultList(eventsToday))
    }.flowOn(Dispatchers.IO)

    override fun descriptionEvent(idEvent: String): Flow<EventDataStatus> = flow {
        emit(EventDataStatus.Loading)
        val aboutEvent = databaseOperations.descriptionEvent(idEvent)
        emit(EventDataStatus.Result(aboutEvent))
    }.flowOn(Dispatchers.IO)
}