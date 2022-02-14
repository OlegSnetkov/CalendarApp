package com.avtograv.calendarapp.ui.calendarFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.avtograv.calendarapp.databinding.FragmentCalendarBinding
import com.avtograv.calendarapp.model.EventModelData
import java.util.*


class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private var clickListener: OnAddEvent? = null
    private lateinit var adapterRecyclerView: AdapterRecyclerView
    private val viewModel: EventsOfDayViewModel by activityViewModels()

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

        adapterRecyclerView = AdapterRecyclerView(AdapterRecyclerView.OnClickListener { event ->
            routeAboutEvent(event)
        })

        viewModel.getEventsOfToday.observe(requireActivity()) { eventsOfDay ->
            adapterRecyclerView.submitList(eventsOfDay)
            binding.recyclerViewEvents.adapter = adapterRecyclerView
        }

        binding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val timestamp = GregorianCalendar(year, month, dayOfMonth).timeInMillis
//            viewModel.deleteAllEvents()
            viewModel.eventsOfDays(timestamp).observe(viewLifecycleOwner) { eventsOfDay ->
                adapterRecyclerView.submitList(eventsOfDay)
                binding.recyclerViewEvents.adapter = adapterRecyclerView
            }
        }
        binding.floatingActionButton.setOnClickListener {
            clickListener?.routeAddEventFragment()
        }
    }

    private fun routeAboutEvent(event: EventModelData) {
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