package io.github.wertylop5.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

abstract class Entry: Parcelable

@Entity
data class Info(
    @PrimaryKey(autoGenerate = true) val infoId: Int = 0,
    var noteId: Int,
    val key: String,
    val value: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(infoId)
        parcel.writeInt(noteId)
        parcel.writeString(key)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Info> {
        override fun createFromParcel(parcel: Parcel): Info {
            return Info(parcel)
        }

        override fun newArray(size: Int): Array<Info?> {
            return arrayOfNulls(size)
        }
    }
}

@Entity
data class Photo(
    @PrimaryKey(autoGenerate = true) val photoId: Int = 0,
    val uri: String,
    val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(photoId)
        parcel.writeString(uri)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }
}

@Entity
data class NoteEntry(
    @PrimaryKey(autoGenerate = true) val noteEntryId: Int = 0,
    val title: String? = null,
    val description: String
) : Entry() {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //super.writeToParcel(parcel, flags)
        parcel.writeInt(noteEntryId)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteEntry> {
        override fun createFromParcel(parcel: Parcel): NoteEntry {
            return NoteEntry(parcel)
        }

        override fun newArray(size: Int): Array<NoteEntry?> {
            return arrayOfNulls(size)
        }
    }
}

data class NoteEntryWithInfo(
    @Embedded val noteEntry: NoteEntry,
    @Relation(
        parentColumn = "noteEntryId",
        entityColumn = "noteId"
    )
    val info: MutableList<Info>
) : Entry() {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(NoteEntry::class.java.classLoader)!!,
        //ArrayList((parcel.readArray(Info::class.java.classLoader))?.toList()),
        parcel.createTypedArrayList(Info.CREATOR) ?: ArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //super.writeToParcel(parcel, flags)
        parcel.writeParcelable(noteEntry, flags)
        parcel.writeTypedList(info)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteEntryWithInfo> {
        override fun createFromParcel(parcel: Parcel): NoteEntryWithInfo {
            return NoteEntryWithInfo(parcel)
        }

        override fun newArray(size: Int): Array<NoteEntryWithInfo?> {
            return arrayOfNulls(size)
        }
    }
}

@Entity
data class PictureEntry(
    @PrimaryKey(autoGenerate = true) val pictureEntryId: Int = 0,
    val title: String? = null,
    val description: String? = null
) : Entry() {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //super.writeToParcel(parcel, flags)
        parcel.writeInt(pictureEntryId)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PictureEntry> {
        override fun createFromParcel(parcel: Parcel): PictureEntry {
            return PictureEntry(parcel)
        }

        override fun newArray(size: Int): Array<PictureEntry?> {
            return arrayOfNulls(size)
        }
    }
}

data class PictureEntryWithPhoto(
    @Embedded val pictureEntry: PictureEntry,
    @Relation(
        parentColumn = "pictureEntryId",
        entityColumn = "photoId"
    )
    val picture: Photo
) : Entry() {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(PictureEntry::class.java.classLoader)!!,
        parcel.readParcelable(Photo::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //super.writeToParcel(parcel, flags)
        parcel.writeParcelable(pictureEntry, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PictureEntryWithPhoto> {
        override fun createFromParcel(parcel: Parcel): PictureEntryWithPhoto {
            return PictureEntryWithPhoto(parcel)
        }

        override fun newArray(size: Int): Array<PictureEntryWithPhoto?> {
            return arrayOfNulls(size)
        }
    }
}

@Entity
data class MilestoneEntry(
    @PrimaryKey(autoGenerate = true) val milestoneEntryId: Int = 0,
    val name: String? = null,
    val description: String) : Entry() {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //super.writeToParcel(parcel, flags)
        parcel.writeInt(milestoneEntryId)
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MilestoneEntry> {
        override fun createFromParcel(parcel: Parcel): MilestoneEntry {
            return MilestoneEntry(parcel)
        }

        override fun newArray(size: Int): Array<MilestoneEntry?> {
            return arrayOfNulls(size)
        }
    }
}

data class MilestoneEntryWithPhoto(
    @Embedded val milestoneEntry: MilestoneEntry,
    @Relation(
        parentColumn = "milestoneEntryId",
        entityColumn = "photoId"
    )
    val picture: Photo?
) : Entry() {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(MilestoneEntry::class.java.classLoader)!!,
        parcel.readParcelable(Photo::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //super.writeToParcel(parcel, flags)
        parcel.writeParcelable(milestoneEntry, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MilestoneEntryWithPhoto> {
        override fun createFromParcel(parcel: Parcel): MilestoneEntryWithPhoto {
            return MilestoneEntryWithPhoto(parcel)
        }

        override fun newArray(size: Int): Array<MilestoneEntryWithPhoto?> {
            return arrayOfNulls(size)
        }
    }
}
