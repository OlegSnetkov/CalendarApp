package com.avtograv.calendarapp.model


data class EventModel(
    val id: String,
    val date_start: Long,
    val date_finish: Long,
    val name: String,
    val description: String
)
