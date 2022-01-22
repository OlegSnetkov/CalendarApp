package com.avtograv.calendarapp.data.realw

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class DatabaseOperations(private val config: RealmConfiguration) {

    suspend fun insertNewEvent(
        dateStart: Long,
        dateFinish: Long,
        name: String,
        description: String?
    ) {
        val realm = Realm.getInstance(config)

        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val newEvent = NewEventRealm(
                dateStart = dateStart,
                dateFinish = dateFinish,
                name = name,
                description = description
            )
            realmTransaction.insert(newEvent)
        }
    }
}