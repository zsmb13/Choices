package co.zsmb.choices.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert
    suspend fun insert(item: Record)

    @Insert
    suspend fun insertAll(items: List<Record>)

    @Query("SELECT count(*) FROM records")
    suspend fun count(): Int

    @Query("SELECT COALESCE(SUM(CASE WHEN score = 1 THEN 1 ELSE -1 END), 0) FROM records")
    fun score(): Flow<Int>

    @Query("SELECT * FROM records")
    fun getAllAsFlow(): Flow<List<Record>>

    @Query("SELECT * FROM records")
    suspend fun getAll(): List<Record>

    @Query("DELETE FROM records")
    suspend fun deleteAll()

    @Query("DELETE FROM records WHERE id = :id")
    suspend fun deleteById(id: Long)
}
