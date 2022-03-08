package com.avtograv.calendarapp.realm

import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class DatabaseOperations {

    suspend fun insertEvent(dateStart: Long, dateFinish: Long, name: String, description: String?) {
        val realm = Realm.getDefaultInstance()
        realm.use {
            realm.executeTransactionAwait(Dispatchers.IO) { realm: Realm ->
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
}