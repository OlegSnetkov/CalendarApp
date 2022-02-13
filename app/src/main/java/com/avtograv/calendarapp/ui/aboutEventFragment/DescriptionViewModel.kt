package com.avtograv.calendarapp.ui.aboutEventFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avtograv.calendarapp.data.realw.EventRealmModel
import com.avtograv.calendarapp.model.EventModelData
import io.realm.Realm


class DescriptionViewModel(private val getIdEvent: String) : ViewModel() {

    val getDescriptionEvent: LiveData<EventModelData>
        get() = descriptionEvent(getIdEvent)

    private fun descriptionEvent(idEvent: String): MutableLiveData<EventModelData> {
        val realm = Realm.getDefaultInstance()
        val realmEvent = MutableLiveData<EventModelData>()
        realm.use {
            val event = realm
                .where(EventRealmModel::class.java)
                .equalTo("id", idEvent)
                .findFirst()!!
            realmEvent.value = mapEvent(event)
        }
        return realmEvent
    }

    private fun mapEvent(event: EventRealmModel): EventModelData {
        return EventModelData(
            id = event.id,
            name = event.name,
            description = event.description!!,
            dateStart = event.dateStart,
            dateFinish = event.dateFinish
        )
    }
}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val getIdEvent: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DescriptionViewModel::class.java)) {
            return DescriptionViewModel(getIdEvent) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}