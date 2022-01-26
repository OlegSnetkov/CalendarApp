package com.avtograv.calendarapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.avtograv.calendarapp.databinding.FragmentAddEventBinding
import com.avtograv.calendarapp.viewmodels.AddEventViewModel

class AddEventFragment : Fragment() {

    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!
    private var clickListener: ClickAddEvent? = null

    private val viewModel: AddEventViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickAddEvent) {
            clickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {

        binding.apply {
            buttonSetDate.setOnClickListener {
                clickListener?.setDatePicker()
            }
            buttonSetTime.setOnClickListener {
                clickListener?.setTimePicker()
            }
            buttonAddEvent.setOnClickListener {
                textInputNameEvent.error = ""
                if (viewModel.isValid(editTextNameEvent.text.toString())) {
                    viewModel.addEvent(
                        123,  // TODO
                        321,
                        editTextNameEvent.text.toString(),
                        editTextDescription.text.toString()
                    )
                    clickListener?.routeCalendarFragment()
                }
            }

            viewModel.allEvents.observe(requireActivity(), {
                // TODO
            })
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface ClickAddEvent {
        fun setTimePicker()
        fun setDatePicker()
        fun routeCalendarFragment()
    }

    companion object {
        fun create() = AddEventFragment()
    }
}