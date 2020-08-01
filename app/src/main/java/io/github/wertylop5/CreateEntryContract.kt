package io.github.wertylop5

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import io.github.wertylop5.model.Entry
import io.github.wertylop5.model.NoteEntry
import io.github.wertylop5.model.NoteEntryWithInfo

class CreateEntryContract: ActivityResultContract<Entry?, Entry?>() {
    override fun createIntent(context: Context, input: Entry?): Intent {
        return Intent(Intent.ACTION_INSERT_OR_EDIT).apply {
            if (input != null)
//                putExtra(GameMainActivity.EXISTING_ENTRY,
//                    EntryParcelableFactory.getEntryParcelable(input))
                putExtra(GameMainActivity.EXISTING_ENTRY, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Entry? {
        if (resultCode != Activity.RESULT_OK) return null

        var parcelable: Parcelable?

        parcelable = intent?.getParcelableExtra(CreateEntryActivity.NEW_ENTRY)
        when (parcelable) {
            //TODO deprecate this
            is NoteEntryParcelable -> {
                return NoteEntry(parcelable.id, parcelable.title, parcelable.description)
            }
            is NoteEntry -> {
                return NoteEntry(parcelable.noteEntryId, parcelable.title, parcelable.description)
            }
            is NoteEntryWithInfo -> {
                return NoteEntryWithInfo(parcelable.noteEntry, parcelable.info)
            }
            else -> {}
        }

        parcelable = intent?.getParcelableExtra(GameMainActivity.EXISTING_ENTRY)
        when (parcelable) {
            is NoteEntry -> {
                return NoteEntry(parcelable.noteEntryId, parcelable.title, parcelable.description)
            }
            is NoteEntryWithInfo -> {
                return NoteEntryWithInfo(parcelable.noteEntry, parcelable.info)
            }
            else -> {}
        }

        return null
    }
}