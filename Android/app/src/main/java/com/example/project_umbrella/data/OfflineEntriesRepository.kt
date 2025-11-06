package com.example.project_umbrella.data

import com.example.project_umbrella.model.Entry
import kotlinx.coroutines.flow.Flow

class OfflineEntriesRepository(private val entryDao: EntryDao): EntriesRepository {
    override suspend fun insertEntry(entry: Entry) = entryDao.insertEntry(entry)

    override fun getEntry(entryId: Int): Flow<Entry>  = entryDao.getEntry(entryId)

    override fun getAllEntries(): Flow<List<Entry>> = entryDao.getAllEntries()
}