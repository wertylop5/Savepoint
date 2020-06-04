package io.github.wertylop5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NoteEntry::class, PictureEntry::class, MilestoneEntry::class],
    version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteEntryDao(): NoteEntryDao

    companion object {
        private lateinit var db: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if (!this::db.isInitialized) {
                db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "AppDatabase"
                ).build()
            }

            return db
        }
    }
}
