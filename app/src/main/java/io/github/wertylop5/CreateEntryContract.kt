package io.github.wertylop5

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import io.github.wertylop5.model.Entry
import io.github.wertylop5.model.NoteEntry

class CreateEntryContract: ActivityResultContract<Entry?, Entry?>() {
    override fun createIntent(context: Context, input: Entry?): Intent {
        return Intent(Intent.ACTION_INSERT_OR_EDIT).apply {
            if (input != null)
                putExtra(GameMainActivity.EXISTING_ENTRY,
                    EntryParcelableFactory.getEntryParcelable(input))
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Entry? {
        if (resultCode != Activity.RESULT_OK) return null

        val parcelable = intent?.getParcelableExtra<Parcelable>(CreateEntryActivity.NEW_ENTRY)
            ?: return null

        return when (parcelable) {
            is NoteEntryParcelable -> {
                NoteEntry(parcelable.id, parcelable.title, parcelable.description)
            }
            else -> {
                null
            }
        }
    }
}