package io.github.wertylop5

import android.os.Parcel
import android.os.Parcelable
import io.github.wertylop5.model.NoteEntry

//TODO rename to just Entry
//TODO turn this into a factory thing to handle different entry types
class NoteEntryParcelable() : EntryParcelable//: Parcelable {
{
    //should use EntryTypes.ordinal
    private var entryType: Int = -1

    var id: Int = 0
        private set

    var title: String? = null
        private set

    lateinit var description: String
        private set

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        title = parcel.readString()

        //this shouldn't ever be null, since NoteEntry.description is type String
        description = parcel.readString() ?: ""
    }

    constructor(noteEntry: NoteEntry): this() {
        id = noteEntry.noteEntryId
        title = noteEntry.title
        description = noteEntry.description
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //super.writeToParcel(parcel, flags)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteEntryParcelable> {
        override fun createFromParcel(parcel: Parcel): NoteEntryParcelable {
            return NoteEntryParcelable(parcel)
        }

        override fun newArray(size: Int): Array<NoteEntryParcelable?> {
            return arrayOfNulls(size)
        }
    }
}

//should use EntryTypes.ordinal
//    private var entryType: Int = -1
//
//    var id: Int = 0
//        private set
//
//    var title: String? = null
//        private set
//
//    lateinit var description: String
//        private set
//
//    constructor(parcel: Parcel) : this() {
//        id = parcel.readInt()
//        title = parcel.readString()
//
//        //this shouldn't ever be null, since NoteEntry.description is type String
//        description = parcel.readString() ?: ""
//    }
//
//    constructor(noteEntry: NoteEntry): this() {
//        id = noteEntry.noteEntryId
//        title = noteEntry.title
//        description = noteEntry.description
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(id)
//        parcel.writeString(title)
//        parcel.writeString(description)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<NoteEntryParcelable> {
//        override fun createFromParcel(parcel: Parcel): NoteEntryParcelable {
//            return NoteEntryParcelable(parcel)
//        }
//
//        override fun newArray(size: Int): Array<NoteEntryParcelable?> {
//            return arrayOfNulls(size)
//        }
//    }
//}