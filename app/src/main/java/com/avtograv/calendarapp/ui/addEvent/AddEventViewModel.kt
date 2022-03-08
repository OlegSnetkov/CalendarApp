package com.avtograv.calendarapp.ui.addEvent

import androidx.lifecycle.*
import com.avtograv.calendarapp.repositories.EventDataStatus
import com.avtograv.calendarapp.repositories.EventRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AddEventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _eventDataStatus = MutableLiveData<EventDataStatus>()
    val petDataStatus: LiveData<EventDataStatus>
        get() {
            return _eventDataStatus
        }

    fun addEvent(dateStart: Long, dateFinish: Long, name: String, description: String?) {
        viewModelScope.launch {
            eventRepository.addEvent(dateStart, dateFinish, name, description).collect {
                _eventDataStatus.value = it
            }
        }
    }

    fun isValid(name: String) = name.isNotEmpty()
}

@Suppress("UNCHECKED_CAST")
class AddEventViewModelFactory(private val eventRepository: EventRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEventViewModel::class.java)) {
            return AddEventViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//    fun addEvent(dateStart: Long, dateFinish: Long, name: String, description: String?) {
//        val realm = Realm.getDefaultInstance()
//        realm.use {
//            realm.executeTransaction { realm: Realm ->
//                val event =
//                    realm.createObject(EventRealmModel::class.java, UUID.randomUUID().toString())
//                event.dateStart = dateStart
//                event.dateFinish = dateFinish
//                event.dateStartStr =
//                    SimpleDateFormat("yyyy.MM.dd", Locale.US).format(Date(dateStart))
//                event.name = name
//                event.description = description
//                realm.insertOrUpdate(event)
//            }
//        }
//    }