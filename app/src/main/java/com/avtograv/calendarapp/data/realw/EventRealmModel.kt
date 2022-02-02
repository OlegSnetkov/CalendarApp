package com.avtograv.calendarapp.data.realw

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import org.bson.types.ObjectId


@RealmClass
open class EventRealmModel : RealmModel {

    @PrimaryKey
    var id: String = ObjectId().toHexString()
    var dateStart: Long = 0
    var dateFinish: Long = 0

    @Required
    var name: String = ""
    var description: String? = null
}