package com.avtograv.calendarapp.repositories

import com.avtograv.calendarapp.data.realw.DatabaseOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class EventRepositoryImpl(private val databaseOperations: DatabaseOperations) : EventRepository {

    override fun addPet(
        dateStart: Long,
        dateFinish: Long,
        name: String,
        description: String?,
    ): Flow<EventDataStatus> = flow {
        emit(EventDataStatus.Loading)
        databaseOperations.insertNewEvent(dateStart, dateFinish, name, description)
        emit(EventDataStatus.Added)
    }.flowOn(Dispatchers.IO)
}