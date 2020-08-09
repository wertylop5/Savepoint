package io.github.wertylop5.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    @Query("SELECT * FROM NoteEntry WHERE rowid = :id")
    suspend fun getNoteEntryByRowid(id: Long): List<NoteEntry>

    //using suspend cuz they're slow?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteEntry(entry: NoteEntry): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteEntries(entries: List<NoteEntry>)

    @Update
    fun updateNoteEntry(entry: NoteEntry)

    @Query("DELETE FROM NoteEntry")
    suspend fun deleteAll()
}

@Dao
interface InfoDao {
    @Query("SELECT * FROM Info")
    fun getAll(): LiveData<MutableList<Info>>

    @Query("SELECT * FROM Info WHERE noteId = :entryId")
    fun getInfoFromEntryId(entryId: Int): LiveData<MutableList<Info>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfoItem(info: Info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfoItems(infos: List<Info>): List<Long>

    @Query("DELETE FROM Info")
    suspend fun deleteAll()
}
