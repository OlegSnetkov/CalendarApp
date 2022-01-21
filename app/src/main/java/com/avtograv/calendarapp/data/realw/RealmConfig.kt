package com.avtograv.calendarapp.data.realw

import io.realm.RealmConfiguration

class RealmConfig {

    private val realmVersion = 2L

    fun providesRealmConfig(): RealmConfiguration =
        RealmConfiguration.Builder()
            .schemaVersion(realmVersion)
//            .migration(migration)
            .build()
}