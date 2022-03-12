package com.avtograv.calendarapp.ui.addEvent

import androidx.lifecycle.*
import com.avtograv.calendarapp.repository.EventDataStatus
import com.avtograv.calendarapp.repository.EventRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AddEventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _eventDataStatus = MutableLiveData<EventDataStatus>()
    val addEventDataStatus: LiveData<EventDataStatus>
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

    fun isValid(dateStart: Long, dateFinish: Long, name: String) =
        name.isNotEmpty() && (dateStart < dateFinish)
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