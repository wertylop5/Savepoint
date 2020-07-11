package io.github.wertylop5

import android.os.Parcelable
import io.github.wertylop5.model.Entry
import io.github.wertylop5.model.NoteEntry

//TODO deprecate this
class EntryParcelableFactory {
    companion object {
        fun getEntryParcelable(entry: Entry): Parcelable {
            when (entry) {
                is NoteEntry -> {
                    return NoteEntryParcelable(entry);
                }
                else -> {
                    throw ClassCastException("entry is not of type Entry");
                }
            }
        }
    }
}