package com.avtograv.calendarapp.repositories

import androidx.lifecycle.MutableLiveData
import com.avtograv.calendarapp.model.EventModelData
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

    override fun eventsOfDays(timestampThisDay: Long): MutableLiveData<List<EventModelData>> {
        TODO("Not yet implemented")
    }

    override fun descriptionEvent(idEvent: String): MutableLiveData<EventModelData> {
        TODO("Not yet implemented")
    }
}