package co.zsmb.choices.data

import dev.zacsweers.metro.Inject
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Inject
class DatabaseSeeder(
    private val dao: RecordDao,
) {
    suspend fun seed() {
        val now = Clock.System.now()
        val records = mutableListOf<Record>()
        val random = Random.Default

        for (i in 0 until 60) {
            val dayStart = now - i.toDuration(DurationUnit.DAYS)
            val entriesCount = random.nextInt(1, 4)

            for (j in 0 until entriesCount) {
                val hourOffset = random.nextInt(0, 24)
                val timestamp = dayStart + hourOffset.toDuration(DurationUnit.HOURS)

                records.add(
                    Record(
                        score = random.nextBoolean(),
                        timestamp = timestamp,
                        comment = if (random.nextBoolean()) "Random data" else null,
                    )
                )
            }
        }
        dao.replaceAll(records)
    }
}
