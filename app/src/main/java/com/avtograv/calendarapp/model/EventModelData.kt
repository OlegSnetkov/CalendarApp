package com.avtograv.calendarapp.model

data class EventModelData(

    val id: String,
    val dateStart: Long,
    val dateFinish: Long,
    val dateStartStr: String,
    val name: String,
    val description: String,
)