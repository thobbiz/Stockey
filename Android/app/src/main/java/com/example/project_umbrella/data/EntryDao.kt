package com.example.project_umbrella.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_umbrella.model.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntry(entry: Entry)

    @Query("SELECT * from entries WHERE id = :entryId")
    fun getEntry(entryId: Int): Flow<Entry>

    @Query("SELECT * from entries ORDER BY id")
    fun getAllEntries(): Flow<List<Entry>>
}