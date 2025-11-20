package co.zsmb.choices.data

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

object InstantTypeConverter {
    @TypeConverter
    fun toEpochMillis(value: Instant): Long = value.toEpochMilliseconds()

    @TypeConverter
    fun fromEpochMillis(value: Long): Instant = Instant.fromEpochMilliseconds(value)
}
