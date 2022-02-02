package com.avtograv.calendarapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avtograv.calendarapp.data.realw.EventRealmModel
import io.realm.Realm
import java.util.*


class EventViewModel : ViewModel() {

    private val realm = Realm.getDefaultInstance()

    val allEvents: LiveData<List<EventRealmModel>>
        get() = getAllEvents()

    fun isValid(name: String) = name.isNotEmpty()

    fun addEvent(dateStart: Long, dateFinish: Long, name: String, description: String?) {

        realm.executeTransaction { realm: Realm ->
            val event =
                realm.createObject(EventRealmModel::class.java, UUID.randomUUID().toString())
            event.dateStart = dateStart
            event.dateFinish = dateFinish
            event.name = name
            event.description = description

            realm.insertOrUpdate(event)
        }
    }

    private fun getAllEvents(): MutableLiveData<List<EventRealmModel>> {
        val list = MutableLiveData<List<EventRealmModel>>()
        val events = realm.where(EventRealmModel::class.java).findAll()
        list.value = events?.subList(0, events.size)
        return list
    }
}