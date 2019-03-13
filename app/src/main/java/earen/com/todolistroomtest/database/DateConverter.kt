package earen.com.todolistroomtest.database

import android.arch.persistence.room.TypeConverter
import java.util.*


class DateConverter {

    @TypeConverter
    fun toDate(timeStamp: Long?): Date? {
        return if (timeStamp == null) null else Date(timeStamp)
    }

    @TypeConverter
    fun toTimeStamp(date: Date?): Long? {
        return (if (date == null) null else date.getTime())!!.toLong()
    }
}