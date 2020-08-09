package io.github.wertylop5.model

import androidx.lifecycle.LiveData

class EntryRepository(private val dao: NoteEntryDao) {
    val noteEntries: LiveData<List<NoteEntry>> = dao.getAllNoInfo()

    suspend fun getNoteEntryByRowid(id: Long): NoteEntry {
        val data = dao.getNoteEntryByRowid(id)
        return data[0]
    }

    suspend fun insertNoteEntry(entry: NoteEntry): Long {
        return dao.insertNoteEntry(entry)
    }

    fun update(note: NoteEntry) {
        dao.updateNoteEntry(note)
    }
}