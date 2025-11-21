package co.zsmb.choices.data

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

class RecordSerializationTest {
    @Test
    fun testSerialization() {
        val record = Record(
            id = 123,
            score = true,
            comment = "Test comment",
            timestamp = Instant.parse("2023-10-27T10:00:00Z")
        )
        val json = Json.encodeToString(record)

        val decoded = Json.decodeFromString<Record>(json)
        assertEquals(record, decoded)
    }
    
    @Test
    fun testListSerialization() {
         val list = listOf(
            Record(id = 1, score = true, comment = "A", timestamp = Instant.fromEpochMilliseconds(1000)),
            Record(id = 2, score = false, comment = "B", timestamp = Instant.fromEpochMilliseconds(2000))
         )
         val json = Json.encodeToString(list)

         val decoded = Json.decodeFromString<List<Record>>(json)
         assertEquals(list, decoded)
    }
}
