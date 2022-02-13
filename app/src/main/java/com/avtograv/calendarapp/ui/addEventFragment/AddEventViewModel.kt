package com.avtograv.calendarapp.ui.addEventFragment

import androidx.lifecycle.ViewModel
import com.avtograv.calendarapp.data.realw.EventRealmModel
import io.realm.Realm
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class AddEventViewModel : ViewModel() {

    fun addEvent(dateStart: Long, dateFinish: Long, name: String, description: String?) {
        val realm = Realm.getDefaultInstance()
        realm.use {
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
    }

    fun isValid(name: String) = name.isNotEmpty()
}