package com.example.project_umbrella.data

import com.example.project_umbrella.model.Entry
import kotlinx.coroutines.flow.Flow

interface EntriesRepository {
    // Insert entry in the data source
    suspend fun insertEntry(entry: Entry)

    // Retrieve a entries with orderProducts from the data source
    fun getEntry(entryId: Int): Flow<Entry>

    // Retrieve all entries from the data source
    fun getAllEntries(): Flow<List<Entry>>
}