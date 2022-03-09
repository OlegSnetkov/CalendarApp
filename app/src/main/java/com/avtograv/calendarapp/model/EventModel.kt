package com.avtograv.calendarapp.model


data class EventModel(
    val id: String,
    val name: String,
    val description: String,
    val dateStart: String,
    val dateFinish: String,
)