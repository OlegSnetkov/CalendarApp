package com.avtograv.calendarapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.avtograv.calendarapp.databinding.FragmentCalendarBinding
import com.avtograv.calendarapp.model.EventModelData
import com.avtograv.calendarapp.viewmodels.EventViewModel
import java.util.*

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private var clickListener: OnAddEvent? = null
    private lateinit var adapterRecyclerView: AdapterRecyclerView
    private val viewModel: EventViewModel by activityViewModels()

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
            viewModel.eventsOfDays(timestamp).observe(requireActivity()) { eventsOfDay ->
                adapterRecyclerView.submitList(eventsOfDay)
                binding.recyclerViewEvents.adapter = adapterRecyclerView
            }
        }
        binding.floatingActionButton.setOnClickListener {
            clickListener?.routeAddEventFragment()
        }
    }

    private fun routeAboutEvent(event: EventModelData) {
        Toast.makeText(requireContext(), "Event ID ${event.id}", Toast.LENGTH_LONG).show()
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
    }

    companion object {
        fun create() = CalendarFragment()
    }
}