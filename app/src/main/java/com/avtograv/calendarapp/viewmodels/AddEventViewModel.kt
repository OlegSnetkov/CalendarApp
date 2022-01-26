package com.avtograv.calendarapp.viewmodels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avtograv.calendarapp.data.realw.NewEventRealm
import io.realm.Realm
import java.util.*


class AddEventViewModel() : ViewModel() {

    private val realm = Realm.getDefaultInstance()

    val allEvents: LiveData<List<NewEventRealm>>
        get() = getAllEvents()

    fun isValid(name: String) = name.isNotEmpty()

    fun addEvent(dateStart: Long, dateFinish: Long, name: String, description: String?) {

        realm.executeTransaction { realm: Realm ->
            val event = realm.createObject(NewEventRealm::class.java, UUID.randomUUID().toString())
            event.dateStart = dateStart
            event.dateFinish = dateFinish
            event.name = name
            event.description = description
            realm.insertOrUpdate(event)
        }
    }

    private fun getAllEvents(): MutableLiveData<List<NewEventRealm>> {
        val list = MutableLiveData<List<NewEventRealm>>()
        val events = realm.where(NewEventRealm::class.java).findAll()
        list.value = events?.subList(0, events.size)
        return list
    }

    fun deleteAllEvents() {
        realm.executeTransaction { r: Realm ->
            r.delete(NewEventRealm::class.java)
        }
    }
}