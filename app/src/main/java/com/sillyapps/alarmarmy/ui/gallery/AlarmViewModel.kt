package com.sillyapps.alarmarmy.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sillyapps.alarmarmy.R
import com.sillyapps.alarmarmy.data.Alarm
import com.sillyapps.alarmarmy.data.Result
import com.sillyapps.alarmarmy.data.local.AlarmDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(private val alarmDao: AlarmDao): ViewModel() {

    private val _alarm = MutableLiveData<Alarm>()
    val alarm: LiveData<Alarm> = _alarm

    fun loadAlarm(id: Long) {
        viewModelScope.launch {
            val dbAlarm = alarmDao.getAlarm(id)

            if (dbAlarm == null) {
                _alarm.postValue(Alarm())
                return@launch
            }
            _alarm.postValue(dbAlarm!!)
        }
    }

    fun saveAlarm(): Result {
        val alarmVal = alarm.value!!

        alarmVal.apply {
            // repeat is set to True, but no particular day is selected
            if (repeat && !(repeatMonday || repeatTuesday || repeatWednesday || repeatThursday || repeatFriday || repeatSaturday || repeatSunday)) {
                repeat = false
            }
        }

        viewModelScope.launch {
            alarmDao.insertAlarm(alarmVal)
        }
        return Result(true)
    }

}