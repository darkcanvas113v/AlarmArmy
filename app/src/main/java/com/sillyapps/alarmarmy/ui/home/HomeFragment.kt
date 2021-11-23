package com.sillyapps.alarmarmy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sillyapps.alarmarmy.R
import com.sillyapps.alarmarmy.data.Alarm
import com.sillyapps.alarmarmy.databinding.FragmentHomeBinding
import com.sillyapps.alarmarmy.ui.ListClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        setupAdapter()

        setupFab()
    }

    private fun setupFab() {
        binding.floatingActionButton.setOnClickListener {
            navigateToAlarmFragment()
        }
    }

    private fun setupAdapter() {
        val onClickListener = ListClickListener {
            navigateToAlarmFragment(viewModel.alarms.value!![it].id)
        }

        val adapter = AlarmAdapter(onClickListener)

        binding.alarmRecView.adapter = adapter

        viewModel.alarms.observe(viewLifecycleOwner, { adapter.submitList(it) })
    }

    private fun navigateToAlarmFragment(alarmId: Long = 0L) {
        val action = HomeFragmentDirections.actionNavHomeToNavEdit(alarmId)
        findNavController().navigate(action)
    }

}