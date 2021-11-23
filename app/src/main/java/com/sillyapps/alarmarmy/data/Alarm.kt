package com.sillyapps.alarmarmy.data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sillyapps.alarmarmy.BR
import timber.log.Timber

@Entity(tableName = "alarms")
class Alarm (@PrimaryKey(autoGenerate = true) val id: Long = 0): BaseObservable() {

    var time: Long = 0L

    @Bindable
    var active: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.active)
        }

    @Bindable
    var repeat: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.repeat)
        }

    @Bindable
    var repeatMonday: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.repeatMonday)
        }

    @Bindable
    var repeatTuesday: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.repeatTuesday)
        }

    @Bindable
    var repeatWednesday: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.repeatWednesday)
        }

    @Bindable
    var repeatThursday: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.repeatThursday)
        }

    @Bindable
    var repeatFriday: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.repeatFriday)
        }

    @Bindable
    var repeatSaturday: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.repeatSaturday)
        }

    @Bindable
    var repeatSunday: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.repeatSunday)
        }

    fun setAllRepeatTo(repeatValue: Boolean) {
        repeatMonday = repeatValue
        repeatTuesday = repeatValue
        repeatWednesday = repeatValue
        repeatThursday = repeatValue
        repeatFriday = repeatValue
        repeatSaturday = repeatValue
        repeatSunday = repeatValue
    }

    fun isAllRepeatSetToFalse(): Boolean {
        if (repeatMonday && repeatTuesday && repeatWednesday && repeatThursday && repeatFriday && repeatSaturday && repeatSunday) return true
        return false
    }

}
