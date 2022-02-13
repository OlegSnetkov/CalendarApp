package com.avtograv.calendarapp.ui.aboutEventFragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.avtograv.calendarapp.databinding.FragmentEventAboutBinding

class DescriptionEventFragment : Fragment() {

    private var _binding: FragmentEventAboutBinding? = null
    private val binding get() = _binding!!
    private var clickListener: OnCalendarFragment? = null

    private lateinit var viewModel: DescriptionViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCalendarFragment) {
            clickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("get_event_description") { _, bundle ->
            val eventId = bundle.getString("event_id")
            viewModelFactory = ViewModelFactory(eventId!!)
            viewModel = ViewModelProvider(this, viewModelFactory)[DescriptionViewModel::class.java]
            viewModel.getDescriptionEvent.observe(viewLifecycleOwner) { eventModelData ->
                binding.tvEventName.text = "Name event: ${eventModelData.name}"
                binding.tvEventDescription.text = "Description event: ${eventModelData.description}"
                binding.tvEventStart.text = "Start event: ${eventModelData.dateStart}"
                binding.tvEventFinish.text = "Finish event: ${eventModelData.dateFinish}"
            }
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

    interface OnCalendarFragment {
//        fun routeCalendarFragment()
    }

    companion object {
        fun create() = DescriptionEventFragment()
    }
}