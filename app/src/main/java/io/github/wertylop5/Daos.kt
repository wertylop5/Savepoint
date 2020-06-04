package io.github.wertylop5

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

data class NoteEntrySummary(
    val title: String? = null,
    val description: String
)

@Dao
interface NoteEntryDao {
    @Transaction                                //cuz it requires two queries
    @Query("SELECT * FROM NoteEntry")
    fun getAll(): List<NoteEntryWithInfo>

    @Query("SELECT title, description FROM NoteEntry")
    fun getSummary(): List<NoteEntrySummary>

    @Insert
    fun insertNoteEntries(entries: List<NoteEntry>)
}
