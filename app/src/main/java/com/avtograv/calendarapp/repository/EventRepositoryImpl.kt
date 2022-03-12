package com.avtograv.calendarapp.repository

import com.avtograv.calendarapp.realm.RealmOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EventRepositoryImpl(private val realmOperations: RealmOperations) : EventRepository {

    override fun addEvent(
        dateStart: Long,
        dateFinish: Long,
        name: String,
        description: String?,
    ): Flow<EventDataStatus> = flow {
        emit(EventDataStatus.Loading)
        realmOperations.insertEvent(dateStart, dateFinish, name, description)
        emit(EventDataStatus.Added)
    }.flowOn(Dispatchers.IO)

    override fun eventsOfDay(timestampThisDay: Long): Flow<EventDataStatus> = flow {
        emit(EventDataStatus.Loading)
        val eventsToday = realmOperations.retrieveEvents(timestampThisDay)
        emit(EventDataStatus.ResultList(eventsToday))
    }.flowOn(Dispatchers.IO)

    override fun descriptionEvent(idEvent: String): Flow<EventDataStatus> = flow {
        emit(EventDataStatus.Loading)
        val aboutEvent = realmOperations.descriptionEvent(idEvent)
        emit(EventDataStatus.Result(aboutEvent))
    }.flowOn(Dispatchers.IO)
}