package com.sillyapps.alarmarmy.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sillyapps.alarmarmy.convertToMillis
import com.sillyapps.alarmarmy.databinding.FragmentAlarmBinding
import com.sillyapps.alarmarmy.getFormattedValuesInRange
import com.sillyapps.alarmarmy.getHoursAndMinutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmFragment : Fragment() {

    private val args: AlarmFragmentArgs by navArgs()
    private val viewModel: AlarmViewModel by viewModels()
    private lateinit var binding: FragmentAlarmBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlarmBinding.inflate(inflater, container, false)

        viewModel.loadAlarm(args.alarmId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.alarm.observe(viewLifecycleOwner) {
            binding.alarm = it

            setTime(it.time)
        }

        setupNumberPickers()
        setupFAB()
    }

    private fun setupFAB() {
        binding.confirmationButton.setOnClickListener {
            saveAlarm()
        }
    }

    private fun setupNumberPickers() {
        val hourValues = getFormattedValuesInRange(24)
        val minuteValues = getFormattedValuesInRange(60)

        binding.hourPicker.apply {
            minValue = 0
            maxValue = 23
            displayedValues = hourValues
        }

        binding.minutePicker.apply {
            minValue = 0
            maxValue = 59
            displayedValues = minuteValues
        }
    }

    private fun setupRepeatButton() {
        binding.switchMaterial.setOnCheckedChangeListener { _, checked ->
            viewModel.alarm.value?.setAllRepeatTo(checked)
        }

        val checkedListener = CompoundButton.OnCheckedChangeListener { _, checked ->
            if (checked && !binding.switchMaterial.isChecked) {
                viewModel.alarm.value!!.repeat = true
                return@OnCheckedChangeListener
            }

            if (!checked && viewModel.alarm.value!!.isAllRepeatSetToFalse()) {
                viewModel.alarm.value!!.repeat = false
            }
        }
        binding.mondayButton.setOnCheckedChangeListener(checkedListener)
        binding.tuesdayButton.setOnCheckedChangeListener(checkedListener)
        binding.wednesdayButton.setOnCheckedChangeListener(checkedListener)
        binding.thursdayButton.setOnCheckedChangeListener(checkedListener)
        binding.fridayButton.setOnCheckedChangeListener(checkedListener)
        binding.saturdayButton.setOnCheckedChangeListener(checkedListener)
        binding.sundayButton.setOnCheckedChangeListener(checkedListener)
    }

    private fun saveAlarm() {

        viewModel.alarm.value?.apply {
            time = getTime()
        }
        val result = viewModel.saveAlarm()

        if (result.success) {
            findNavController().popBackStack()
            return
        }

        Toast.makeText(context, result.messageId, Toast.LENGTH_SHORT).show()
    }

    private fun getTime(): Long {
        val hours = binding.hourPicker.value
        val minutes = binding.minutePicker.value

        return convertToMillis(hours, minutes)
    }

    private fun setTime(time: Long) {
        val hourAndMinutes = getHoursAndMinutes(time)

        binding.hourPicker.value = hourAndMinutes.first
        binding.minutePicker.value = hourAndMinutes.second
    }
}