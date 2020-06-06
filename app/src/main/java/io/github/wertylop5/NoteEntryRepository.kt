package io.github.wertylop5

import androidx.lifecycle.LiveData

class NoteEntryRepository(private val dao: NoteEntryDao) {
    val noteEntries: LiveData<List<NoteEntry>> = dao.getAllNoInfo()

    suspend fun insert(note: NoteEntry) {
        dao.insertNoteEntry(note)
    }
}