package co.zsmb.choices.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Entity(tableName = "records")
@Serializable
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val score: Boolean,
    val comment: String?,
    val timestamp: Instant,
)
