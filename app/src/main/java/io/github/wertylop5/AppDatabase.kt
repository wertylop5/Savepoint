package io.github.wertylop5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Info::class, Photo::class,
        NoteEntry::class, PictureEntry::class, MilestoneEntry::class],
    version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteEntryDao(): NoteEntryDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            DB_INSTANCE.let {
                scope.launch {
                    populateDatabase(it.noteEntryDao())
                }
            }
        }

        suspend fun populateDatabase(dao: NoteEntryDao) {
            dao.deleteAll()

            val entryList = ArrayList<NoteEntry>()
            for (x in 0..20) {
                entryList.add(NoteEntry(
                    title="big title",
                    description = "Test$x Test$x Test$x Test$x Test$x Test$x Test$x Test$x Test$x Test$x Test$x"))
            }

            dao.insertNoteEntries(entryList)
        }
    }

    companion object {
        private lateinit var DB_INSTANCE: AppDatabase

        //scope is needed to launch coroutines
        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            if (!this::DB_INSTANCE.isInitialized) {
                DB_INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "AppDatabase"
                ).addCallback(AppDatabaseCallback(scope)).build()
            }

            return DB_INSTANCE
        }
    }
}
