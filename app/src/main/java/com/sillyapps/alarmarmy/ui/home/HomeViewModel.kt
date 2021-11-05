package com.sillyapps.alarmarmy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sillyapps.alarmarmy.data.local.AlarmDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alarmDao: AlarmDao): ViewModel() {

    val alarms = alarmDao.observeAllAlarms()

}