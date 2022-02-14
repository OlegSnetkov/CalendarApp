package com.avtograv.calendarapp.ui.calendarFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avtograv.calendarapp.data.realw.EventRealmModel
import com.avtograv.calendarapp.model.EventModelData
import io.realm.Realm
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class EventsOfDayViewModel : ViewModel() {

    private val calendar = Calendar.getInstance()
    val getEventsOfToday: LiveData<List<EventModelData>>
        get() = eventsOfDays(calendar.timeInMillis)

    private fun mapEvent(event: EventRealmModel): EventModelData {
        val dayStart = SimpleDateFormat("dd",
            Locale.US).format(java.util.Date(event.dateStart))
        val dayFinish = SimpleDateFormat("dd",
            Locale.US).format(java.util.Date(event.dateFinish))

        if (dayStart.equals(dayFinish)) {
            val dateStartStr =
                SimpleDateFormat("HH:mm", Locale.US).format(java.util.Date(event.dateStart))
        }
        return EventModelData(
            id = event.id,
            name = event.name,
            description = event.description!!,
            dateStart = SimpleDateFormat("HH:mm",
                Locale.US).format(java.util.Date(event.dateStart)),
            dateFinish = SimpleDateFormat("HH:mm",
                Locale.US).format(java.util.Date(event.dateFinish))
        )
    }

    fun eventsOfDays(timestampThisDay: Long): MutableLiveData<List<EventModelData>> {
        val realm = Realm.getDefaultInstance()
        val listEvents = MutableLiveData<List<EventModelData>>()
        val dateToday = SimpleDateFormat("yyyy.MM.dd", Locale.US).format(Date(timestampThisDay))
        realm.use {
            val events = realm
                .where(EventRealmModel::class.java)
                .equalTo("dateStartStr", dateToday)
                .sort("dateStart")
                .findAll()
                .map {
                    mapEvent(it)
                }

            listEvents.value = events.subList(0, events.size)
        }
        return listEvents
    }

//    fun deleteAllEvents() {
//        val realm = Realm.getDefaultInstance()
//        realm.use {
//            realm.executeTransaction { realm: Realm ->
//                realm.delete(EventRealmModel::class.java)
//            }
//        }
//    }
}