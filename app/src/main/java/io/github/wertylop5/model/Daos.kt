package io.github.wertylop5.model

import androidx.lifecycle.LiveData
import androidx.room.*

data class NoteEntrySummary(
    val title: String? = null,
    val description: String
)

@Dao
interface NoteEntryDao {
    @Transaction                                //cuz it requires two queries
    @Query("SELECT * FROM NoteEntry")
    fun getAll(): LiveData<List<NoteEntryWithInfo>>

    @Query("SELECT noteEntryId, title, description FROM NoteEntry")
    fun getAllNoInfo(): LiveData<List<NoteEntry>>

    @Query("SELECT title, description FROM NoteEntry")
    fun getSummary(): List<NoteEntrySummary>

    //using suspend cuz they're slow?
    @Insert
    suspend fun insertNoteEntry(entry: NoteEntry)

    @Insert
    suspend fun insertNoteEntries(entries: List<NoteEntry>)

    @Query("DELETE FROM NoteEntry")
    suspend fun deleteAll()
}
