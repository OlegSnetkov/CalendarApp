package com.avtograv.calendarapp.ui.aboutEvent

import androidx.lifecycle.*
import com.avtograv.calendarapp.repository.EventDataStatus
import com.avtograv.calendarapp.repository.EventRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AboutEventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _aboutEventDataStatus = MutableLiveData<EventDataStatus>()
    val aboutEventDataStatus: LiveData<EventDataStatus>
        get() {
            return _aboutEventDataStatus
        }

    fun aboutEvent(idEvent: String) {
        viewModelScope.launch {
            eventRepository.descriptionEvent(idEvent).collect {
                _aboutEventDataStatus.value = it
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class AboutEventViewModelFactory(private val eventRepository: EventRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AboutEventViewModel::class.java)) {
            return AboutEventViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}