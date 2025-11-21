package co.zsmb.choices.data

import androidx.room.TypeConverter
import kotlin.time.Instant

object InstantTypeConverter {
    @TypeConverter
    fun toEpochMillis(value: Instant): Long = value.toEpochMilliseconds()

    @TypeConverter
    fun fromEpochMillis(value: Long): Instant = Instant.fromEpochMilliseconds(value)
}
