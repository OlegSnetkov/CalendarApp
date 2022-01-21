package com.avtograv.calendarapp.data.realw

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import org.bson.types.ObjectId
import org.jetbrains.annotations.NotNull


open class NewCaseRealm(
    @PrimaryKey
    var id: String = ObjectId().toHexString(),
    @NotNull
    var dateStart: Long = 0,
    @NotNull
    var dateFinish: Long = 0,
    @Required
    var name: String = "",
    var description: String? = null
) : RealmObject()