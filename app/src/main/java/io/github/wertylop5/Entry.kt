package io.github.wertylop5

import android.net.Uri
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

abstract class Entry

@Entity
data class Info(
    @PrimaryKey(autoGenerate = true) val noteId: Int = 0,
    val key: String,
    val value: String
)

@Entity
data class Photo(
    @PrimaryKey(autoGenerate = true) val photoId: Int = 0,
    val uri: String,
    val name: String
)

@Entity
data class NoteEntry(
    @PrimaryKey(autoGenerate = true) val noteEntryId: Int = 0,
    val title: String? = null,
    val description: String
) : Entry()

data class NoteEntryWithInfo(
    @Embedded val noteEntry: NoteEntry,
    @Relation(
        parentColumn = "noteEntryId",
        entityColumn = "noteId"
    )
    val info: List<Info>
) : Entry()

@Entity
data class PictureEntry(
    @PrimaryKey(autoGenerate = true) val pictureEntryId: Int = 0,
    val title: String? = null,
    val description: String? = null
) : Entry()

data class PictureEntryWithPhoto(
    @Embedded val pictureEntry: PictureEntry,
    @Relation(
        parentColumn = "pictureEntryId",
        entityColumn = "photoId"
    )
    val picture: Photo?
) : Entry()

@Entity
data class MilestoneEntry(
    @PrimaryKey(autoGenerate = true) val milestoneEntryId: Int = 0,
    val name: String? = null,
    val description: String) : Entry()

data class MilestoneEntryWithPhoto(
    @Embedded val milestoneEntry: MilestoneEntry,
    @Relation(
        parentColumn = "milestoneEntryId",
        entityColumn = "photoId"
    )
    val picture: Photo?
) : Entry()
