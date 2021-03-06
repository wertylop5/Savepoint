package io.github.wertylop5

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.wertylop5.adapters.InfoAdapter
import io.github.wertylop5.model.*
import io.github.wertylop5.recyclerViewUtil.InfoDetailsLookup
import io.github.wertylop5.recyclerViewUtil.InfoKeyProvider

class CreateEntryActivity : AppCompatActivity() {
    private val TAG: String = CreateEntryActivity::class.java.name

    private lateinit var titleText: EditText
    private lateinit var descText: EditText

    private lateinit var infoAdapter: InfoAdapter
    private lateinit var infoRecyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: CreateEntryViewModel

    private lateinit var defaultKeyString: String
    private lateinit var defaultValueString: String

    private lateinit var tracker: SelectionTracker<Info>

    private var editEntry: Entry? = null

    private val SELECTION_ID: String = "infoListSelectionTracker"

    companion object {
        lateinit var NEW_ENTRY: String;
    }

    init {
        NEW_ENTRY = "NEW_ENTRY"
    }

    private fun generateNewNoteEntry(
        title: String, desc: String, noteId: Int? = null): NoteEntry {
        val newNoteEntry: NoteEntry

        if (noteId != null) {
            newNoteEntry = NoteEntry(
                noteEntryId = noteId,
                title = title,
                description = desc
            )
        }
        else {
            newNoteEntry = NoteEntry(
                title = title,
                description = desc
            )
        }

        return newNoteEntry
    }

    private fun generateNewNoteEntryWithInfo(
        noteEntry: NoteEntry, info: MutableList<Info>): NoteEntryWithInfo {
        val res: NoteEntryWithInfo

        return NoteEntryWithInfo(noteEntry, info)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        titleText = findViewById(R.id.create_note_title_edittext)
        descText = findViewById(R.id.create_note_desc_edittext)

        viewManager = LinearLayoutManager(this)

        //setup info list
        infoAdapter = InfoAdapter()
        infoRecyclerView = findViewById<RecyclerView>(R.id.info_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            this.adapter = infoAdapter
        }

        //handle edit entry setup
        if (intent.hasExtra(GameMainActivity.EXISTING_ENTRY)) {
            editEntry = intent.getParcelableExtra(GameMainActivity.EXISTING_ENTRY)

            titleText.setText( when (editEntry) {
                is NoteEntry -> (editEntry as NoteEntry).title ?: ""
                else -> ""
            } )

            descText.setText( when (editEntry) {
                is NoteEntry -> (editEntry as NoteEntry).description
                else -> ""
            } )

//            if (editEntry != null && editEntry is NoteEntry) {
//                Log.d(TAG, "getting existing info")
//                viewModel.getInfoFromEntryId((editEntry as NoteEntry).noteEntryId)
//            }
        }

        //setup viewmodel
        viewModel = ViewModelProvider(this).get(CreateEntryViewModel::class.java)
        viewModel.clearTempInfo()
        viewModel.refreshData(when (editEntry) {
            is NoteEntry -> (editEntry as NoteEntry).noteEntryId
            else -> CreateEntryViewModel.REFRESH_NO_EXISTING_ENTRY
        })
        infoAdapter.setInfoElems(viewModel.listOfInfoToAdd)

        viewModel.listOfInfo.observe(this, Observer {
            Log.d(TAG, "info list changed")

            if (viewModel.shouldDisplayInfoFromDb) {
                it?.let {
                    Log.d(TAG, it.toString())
                    Log.d(TAG, it.size.toString())
                    infoAdapter.setInfoElems(it)
                }
            }
        })

        defaultKeyString = getString(R.string.default_info_key)
        defaultValueString = getString(R.string.default_info_value)

        tracker = SelectionTracker.Builder(SELECTION_ID,
            infoRecyclerView,
            InfoKeyProvider(infoRecyclerView),
            InfoDetailsLookup(infoRecyclerView),
            StorageStrategy.createParcelableStorage(Info::class.java)
        ).withOnItemActivatedListener{item, e ->
            val infoItem = item.selectionKey
            val message = when (infoItem) {
                is Info -> infoItem.value
                else -> ""
            }

            Log.d(TAG, message)
            true
        }.build()
        infoAdapter.tracker = tracker

        findViewById<Button>(R.id.note_add_info_button).setOnClickListener {
            val newInfo = Info(
                noteId = if (editEntry != null && editEntry is NoteEntry) (editEntry as NoteEntry).noteEntryId else -1,
                key = defaultKeyString,
                value = defaultValueString
            )
            viewModel.insert(newInfo)
            infoAdapter.insertInfoElem(newInfo)
        }

        findViewById<Button>(R.id.note_create_button).setOnClickListener {
            val title: String = titleText.text.toString()
            val desc: String = descText.text.toString()
            val info: MutableList<Info> = ArrayList()
            info.addAll(viewModel.listOfInfoToAdd)
            info.addAll(viewModel.listOfInfo.value ?: emptyList())

            val newNote: NoteEntry

            newNote = when (editEntry) {
                is NoteEntry -> generateNewNoteEntry(
                    title, desc, (editEntry as NoteEntry).noteEntryId)
                else -> generateNewNoteEntry(title, desc)
            }

            val intent = Intent()
            if (info.size == 0) {
                intent.putExtra(
                    if (editEntry != null) GameMainActivity.EXISTING_ENTRY else NEW_ENTRY,
                    newNote)
            }
            else {
                // TODO: move info noteId setting into GameMainACtivity after entry insertion
                info.forEach {
                    it.noteId = newNote.noteEntryId
                }

                val res = generateNewNoteEntryWithInfo(newNote, info)
                intent.putExtra(
                    if (editEntry != null) GameMainActivity.EXISTING_ENTRY else NEW_ENTRY,
                    res)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
