package io.github.wertylop5

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

abstract class Entry

@Entity
data class Info(
    @PrimaryKey val noteId: Int,
    val key: String,
    val value: String
)

@Entity
data class NoteEntry(
    @PrimaryKey val noteEntryId: Int,
    val title: String? = null,
    val description: String) : Entry()

data class NoteEntryWithInfo(
    @Embedded val noteEntry: NoteEntry,
    @Relation(
        parentColumn = "noteEntryId",
        entityColumn = "noteId"
    )
    val info: List<Info>
)

@Entity
data class PictureEntry(
    @PrimaryKey val pictureEntryId: Int,
    val title: String? = null,
    val description: String? = null,
    val picture: Photo) : Entry()

@Entity
data class MilestoneEntry(
    @PrimaryKey val milestoneEntryId: Int,
    val name: String? = null,
    val description: String,
    val picture: Photo? = null) : Entry()
