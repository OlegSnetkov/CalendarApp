package com.avtograv.calendarapp.ui.aboutEventFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.avtograv.calendarapp.R
import com.avtograv.calendarapp.databinding.FragmentEventAboutBinding
import com.avtograv.calendarapp.model.EventModelData


class DescriptionEventFragment : Fragment() {

    private var _binding: FragmentEventAboutBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DescriptionViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("get_event_description") { _, bundle ->
            val eventId = bundle.getString("event_id")
            viewModelFactory = ViewModelFactory(eventId!!)
            viewModel = ViewModelProvider(this, viewModelFactory)[DescriptionViewModel::class.java]
            viewModel.getDescriptionEvent.observe(viewLifecycleOwner) { eventModelData ->
                binding.greeting.setContent {
                    DetailsAboutEvent(eventModelData)
                }
            }
        }
    }

    @Composable
    private fun DetailsAboutEvent(event: EventModelData) {
        Column {
            Text(
                text = "Name event: ${event.name}",
                modifier = Modifier
                    .padding(
                        dimensionResource(R.dimen.space_normal)
                    )
            )
            Text(
                text = "Description event: ${event.description}",
                modifier = Modifier
                    .padding(
                        dimensionResource(R.dimen.space_normal)
                    )
            )
            Text(
                text = "Start event: ${event.dateStart}",
                modifier = Modifier
                    .padding(
                        dimensionResource(R.dimen.space_normal)
                    )
            )
            Text(
                text = "Finish event: ${event.dateFinish}",
                modifier = Modifier
                    .padding(
                        dimensionResource(R.dimen.space_normal)
                    )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun create() = DescriptionEventFragment()
    }
}