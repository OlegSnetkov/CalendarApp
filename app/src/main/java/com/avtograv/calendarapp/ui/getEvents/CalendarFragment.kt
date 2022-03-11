package com.avtograv.calendarapp.ui.getEvents

import android.content.Context
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.avtograv.calendarapp.databinding.FragmentCalendarBinding
import com.avtograv.calendarapp.model.EventModel
import com.avtograv.calendarapp.realm.DatabaseOperations
import com.avtograv.calendarapp.repository.EventDataStatus
import com.avtograv.calendarapp.repository.EventRepositoryImpl


class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private var clickListener: OnAddEvent? = null
    private lateinit var viewModel: GetEventsViewModel
    private lateinit var eventListAdapter: Adapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAddEvent) {
            clickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiComponentsSetup()
        viewModelSetup()
    }

    override fun onResume() {
        super.onResume()
        viewModel.eventsOfDays(Calendar.getInstance().timeInMillis)
    }

    private fun viewModelSetup() {
        val dbRealm = DatabaseOperations()
        val repository = EventRepositoryImpl(dbRealm)
        val viewModelFactory = CalendarViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[GetEventsViewModel::class.java]
        viewModel.eventsTodayDataStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                // TODO
                is EventDataStatus.ResultList -> eventListAdapter.submitList(status.eventList)
            }
        }
    }

    private fun uiComponentsSetup() {
        binding.apply {
            floatingActionButton.setOnClickListener {
                clickListener?.routeAddEventFragment()
            }
            calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val timestamp = GregorianCalendar(year, month, dayOfMonth).timeInMillis
                viewModel.eventsOfDays(timestamp)
            }

            itemEventList.layoutManager = LinearLayoutManager(requireContext())
            eventListAdapter = Adapter(Adapter.OnClickListener { event ->
                routeAboutEvent(event)
            })
            itemEventList.adapter = eventListAdapter
        }
    }

    private fun routeAboutEvent(event: EventModel) {
        clickListener?.routeEventAboutFragment()
        setFragmentResult("get_event_description", bundleOf("event_id" to event.id))
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnAddEvent {
        fun routeAddEventFragment()
        fun routeEventAboutFragment()
    }

    companion object {
        fun create() = CalendarFragment()
    }
}