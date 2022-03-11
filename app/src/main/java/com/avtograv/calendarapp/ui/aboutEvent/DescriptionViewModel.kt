package com.avtograv.calendarapp.ui.aboutEvent

import androidx.lifecycle.*
import com.avtograv.calendarapp.repository.EventDataStatus
import com.avtograv.calendarapp.repository.EventRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DescriptionViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _eventDataStatus = MutableLiveData<EventDataStatus>()
    val descriptionDataStatus: LiveData<EventDataStatus>
        get() {
            return _eventDataStatus
        }

    fun aboutEvent(idEvent: String) {
        viewModelScope.launch {
            eventRepository.descriptionEvent(idEvent).collect {
                _eventDataStatus.value = it
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class DescriptionViewModelFactory(private val eventRepository: EventRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DescriptionViewModel::class.java)) {
            return DescriptionViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}