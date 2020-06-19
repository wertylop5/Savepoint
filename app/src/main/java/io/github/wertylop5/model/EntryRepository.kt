package io.github.wertylop5.model

import androidx.lifecycle.LiveData

class EntryRepository(private val dao: NoteEntryDao) {
    val noteEntries: LiveData<List<NoteEntry>> = dao.getAllNoInfo()

    suspend fun insert(note: NoteEntry) {
        dao.insertNoteEntry(note)
    }
}