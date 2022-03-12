package com.avtograv.calendarapp.realm

import com.avtograv.calendarapp.model.EventModel
import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class RealmOperations {

    private lateinit var eventRealmModel: EventRealmModel

    suspend fun retrieveEvents(timestampToday: Long): List<EventModel> {
        val realm = Realm.getDefaultInstance()
        val filteredEvents = mutableListOf<EventModel>()
        val dateToday = SimpleDateFormat("yyyy.MM.dd", Locale.US).format(Date(timestampToday))
        realm.use {
            realm.executeTransactionAwait(Dispatchers.IO) { realm ->
                filteredEvents.addAll(realm
                    .where(EventRealmModel::class.java)
                    .equalTo("dateStartStr", dateToday)
                    .sort("dateStart")
                    .findAll()
                    .map {
                        mapEvent(it)
                    })
            }
        }
        return filteredEvents
    }

    suspend fun insertEvent(dateStart: Long, dateFinish: Long, name: String, description: String?) {
        val realm = Realm.getDefaultInstance()
        realm.use {
            realm.executeTransactionAwait(Dispatchers.IO) { realm ->
                val event =
                    realm.createObject(EventRealmModel::class.java, UUID.randomUUID().toString())
                event.dateStart = dateStart
                event.dateFinish = dateFinish
                event.dateStartStr =
                    SimpleDateFormat("yyyy.MM.dd", Locale.US).format(Date(dateStart))
                event.name = name
                event.description = description
                realm.insertOrUpdate(event)
            }
        }
    }

    suspend fun descriptionEvent(idEvent: String): EventModel {
        val realm = Realm.getDefaultInstance()
        realm.use {
            realm.executeTransactionAwait(Dispatchers.IO) { realm ->
                eventRealmModel = realm
                    .where(EventRealmModel::class.java)
                    .equalTo("id", idEvent)
                    .findFirst()!!
            }
            return mapEvent(eventRealmModel)
        }
    }

    private fun mapEvent(event: EventRealmModel): EventModel {
        return EventModel(
            id = event.id,
            name = event.name,
            description = event.description!!,
            dateStart = SimpleDateFormat("HH:mm",
                Locale.US).format(java.util.Date(event.dateStart)),
            dateFinish = SimpleDateFormat("HH:mm",
                Locale.US).format(java.util.Date(event.dateFinish))
        )
    }

    suspend fun deleteAllEvents() {
        val realm = Realm.getDefaultInstance()
        realm.use {
            realm.executeTransaction { realm: Realm ->
                realm.delete(EventRealmModel::class.java)
            }
        }
    }
}