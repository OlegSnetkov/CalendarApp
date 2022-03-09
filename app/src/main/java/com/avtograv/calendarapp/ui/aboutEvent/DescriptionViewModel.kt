package com.avtograv.calendarapp.ui.aboutEvent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avtograv.calendarapp.realm.EventRealmModel
import com.avtograv.calendarapp.model.EventModel
import io.realm.Realm
import java.text.SimpleDateFormat
import java.util.*


class DescriptionViewModel(private val getIdEvent: String) : ViewModel() {

    val getDescriptionEvent: LiveData<EventModel>
        get() = descriptionEvent(getIdEvent)

    private fun descriptionEvent(idEvent: String): MutableLiveData<EventModel> {
        val realm = Realm.getDefaultInstance()
        val realmEvent = MutableLiveData<EventModel>()
        realm.use {
            val event = realm
                .where(EventRealmModel::class.java)
                .equalTo("id", idEvent)
                .findFirst()!!
            realmEvent.value = mapEvent(event)
        }
        return realmEvent
    }

    private fun mapEvent(event: EventRealmModel): EventModel {
        return EventModel(
            id = event.id,
            name = event.name,
            description = event.description!!,
            dateStart = SimpleDateFormat("HH:mm", Locale.US).format(Date(event.dateStart)),
            dateFinish = SimpleDateFormat("HH:mm", Locale.US).format(Date(event.dateFinish))
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