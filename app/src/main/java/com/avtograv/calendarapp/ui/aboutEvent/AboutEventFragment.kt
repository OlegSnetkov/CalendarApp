package com.avtograv.calendarapp.ui.aboutEvent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.avtograv.calendarapp.model.EventModel
import com.avtograv.calendarapp.realm.RealmOperations
import com.avtograv.calendarapp.repository.EventDataStatus
import com.avtograv.calendarapp.repository.EventRepositoryImpl


class AboutEventFragment : Fragment() {

    private var _binding: FragmentEventAboutBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AboutEventViewModel
    private var eventId: String? = null

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
        viewModelSetup()

        setFragmentResultListener("get_event_description") { _, bundle ->
            eventId = bundle.getString("event_id")
            viewModel.aboutEventDataStatus.observe(viewLifecycleOwner) { status ->
                when (status) {
                    EventDataStatus.Loading -> Toast.makeText(requireContext(),
                        getString(R.string.loaded_description),
                        Toast.LENGTH_SHORT).show()
                    is EventDataStatus.Result -> binding.greeting.setContent {
                        Log.d("statusResult", status.toString())
                        DetailsAboutEvent(status.event)
                    }
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.aboutEvent(eventId!!)
    }

    private fun viewModelSetup() {
        val dbRealm = RealmOperations()
        val repository = EventRepositoryImpl(dbRealm)
        val viewModelFactory = AboutEventViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AboutEventViewModel::class.java]
    }

    @Composable
    private fun DetailsAboutEvent(event: EventModel) {
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
        fun create() = AboutEventFragment()
    }
}