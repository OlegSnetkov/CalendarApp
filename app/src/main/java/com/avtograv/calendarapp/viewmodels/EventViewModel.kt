package com.avtograv.calendarapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avtograv.calendarapp.data.realw.EventRealmModel
import com.avtograv.calendarapp.model.CalendarEvent
import io.realm.Realm
import io.realm.kotlin.deleteFromRealm
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class EventViewModel : ViewModel() {

    private val realm = Realm.getDefaultInstance()
    private val calendar = Calendar.getInstance()
    val getEventsOfToday: LiveData<List<EventRealmModel>>
        get() = eventsToday(calendar.timeInMillis)

    private fun eventsToday(timestamp: Long): MutableLiveData<List<EventRealmModel>> {
        val realm = Realm.getDefaultInstance()
        val list = MutableLiveData<List<EventRealmModel>>()
        val dateToday = SimpleDateFormat("yyyy.MM.dd", Locale.US).format(Date(timestamp))
        realm.use {
            val events = realm
                .where(EventRealmModel::class.java)
                .equalTo("dateStartStr", dateToday)
                .findAll()
            list.value = events?.subList(0, events.size)
        }
        return list
    }

    fun eventsOfDays(timestampThisDay: Long): List<CalendarEvent> {
        val realm = Realm.getDefaultInstance()
        val listEvents = mutableListOf<CalendarEvent>()
        val dateToday = SimpleDateFormat("yyyy.MM.dd", Locale.US).format(Date(timestampThisDay))

        realm.executeTransaction() { realmTransaction ->
            listEvents.addAll(realmTransaction
                .where(EventRealmModel::class.java)
                .equalTo("dateStartStr", dateToday)
                .findAll()
                .map {
                    mapEvent(it)
                }
            )
        }
        return listEvents
    }

    private fun mapEvent(event: EventRealmModel): CalendarEvent {
        return CalendarEvent(
            id = event.id,
            name = event.name,
            description = event.description!!,
            dateStart = event.dateStart,
            dateFinish = event.dateFinish,
            dateStartStr = event.dateStartStr
        )
    }

    fun addEvent(dateStart: Long, dateFinish: Long, name: String, description: String?) {
        realm.executeTransaction { realm: Realm ->
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

    fun updateEvent(
        id: String,
        dateStart: Long,
        dateFinish: Long,
        name: String,
        description: String?,
    ) {
        val target = realm.where(EventRealmModel::class.java)
            .equalTo("id", id).findFirst()
        realm.executeTransaction {
            target!!.dateStart = dateStart
            target.dateFinish = dateFinish
            target.dateStartStr =
                SimpleDateFormat("yyyy.MM.dd", Locale.US).format(Date(dateStart))
            target.name = name
            target.description = description
            realm.insertOrUpdate(target)
        }
    }

    fun deleteEvent(id: String) {
        val notes = realm.where(EventRealmModel::class.java)
            .equalTo("id", id)
            .findFirst()

        realm.executeTransaction {
            notes!!.deleteFromRealm()
        }
    }

    fun deleteAllEvent() {
        realm.executeTransaction { realm: Realm ->
            realm.delete(EventRealmModel::class.java)
        }
    }

    fun isValid(name: String) = name.isNotEmpty()
}