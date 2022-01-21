package com.avtograv.calendarapp.data.realw

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class TodoDatabaseOperations(private val config: RealmConfiguration) {

    suspend fun insertNewCase(
        dateStart: Long,
        dateFinish: Long,
        name: String,
        description: String?
    ) {
        val realm = Realm.getInstance(config)

        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val newCase = NewCaseRealm(
                dateStart = dateStart,
                dateFinish = dateFinish,
                name = name,
                description = description
            )
            realmTransaction.insert(newCase)
        }
    }
}