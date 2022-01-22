package com.avtograv.calendarapp.repositories

import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun addPet(
        dateStart: Long,
        dateFinish: Long,
        name: String,
        description: String?,
    ): Flow<EventDataStatus>
}