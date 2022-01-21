package com.avtograv.calendarapp

import android.app.Application
import io.realm.Realm

class CalendarApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}