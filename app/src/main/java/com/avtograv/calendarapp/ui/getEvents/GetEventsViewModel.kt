package com.avtograv.calendarapp.ui.getEvents

import androidx.lifecycle.*
import com.avtograv.calendarapp.repository.EventDataStatus
import com.avtograv.calendarapp.repository.EventRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class GetEventsViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _eventDataStatus = MutableLiveData<EventDataStatus>()
    val eventsTodayDataStatus: LiveData<EventDataStatus>
        get() {
            return _eventDataStatus
        }

    fun eventsOfDays(timestampThisDay: Long) {
        viewModelScope.launch {
            eventRepository.eventsOfDay(timestampThisDay).collect {
                _eventDataStatus.value = it
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CalendarViewModelFactory(private val eventRepository: EventRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetEventsViewModel::class.java)) {
            return GetEventsViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}