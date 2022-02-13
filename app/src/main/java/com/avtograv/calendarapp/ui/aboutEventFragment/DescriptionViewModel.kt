package com.avtograv.calendarapp.ui.aboutEventFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avtograv.calendarapp.data.realw.EventRealmModel
import io.realm.Realm


class DescriptionViewModel(private val getIdEvent: String) : ViewModel() {

    val getDescriptionEvent: LiveData<EventRealmModel>
        get() = descriptionEvent(getIdEvent)

    private fun descriptionEvent(idEvent: String): MutableLiveData<EventRealmModel> {
        val realm = Realm.getDefaultInstance()
        val realmEvent = MutableLiveData<EventRealmModel>()
        realm.use {
            val event = realm
                .where(EventRealmModel::class.java)
                .equalTo("id", idEvent)
                .findFirst()!!
            realmEvent.value = event
        }

        Log.d("mutableLiveData in viewModel", realmEvent.toString())

        return realmEvent
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