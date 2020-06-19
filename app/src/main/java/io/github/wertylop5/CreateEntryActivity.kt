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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.wertylop5.model.CreateEntryViewModel
import io.github.wertylop5.model.Info
import io.github.wertylop5.model.NoteEntry

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

    companion object {
        lateinit var NEW_ENTRY: String;
    }

    init {
        NEW_ENTRY = "NEW_ENTRY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        titleText = findViewById(R.id.create_note_title_edittext)
        descText = findViewById(R.id.create_note_desc_edittext)

        infoAdapter = InfoAdapter()
        viewManager = LinearLayoutManager(this)
        infoRecyclerView = findViewById<RecyclerView>(R.id.info_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            this.adapter = infoAdapter
        }

        viewModel = ViewModelProvider(this).get(CreateEntryViewModel::class.java)
        viewModel.listOfInfo.observe(this, Observer {
            it?.let {
                Log.d(TAG, it.size.toString())
                infoAdapter.setInfoElems(it)
            }
        })

        defaultKeyString = getString(R.string.default_info_key)
        defaultValueString = getString(R.string.default_info_value)

        findViewById<Button>(R.id.note_add_info_button).setOnClickListener {
            viewModel.insert(
                Info(
                    key = defaultKeyString,
                    value = defaultValueString
                )
            )
        }

        findViewById<Button>(R.id.note_create_button).setOnClickListener {
            val title: String = titleText.text.toString()
            val desc: String = descText.text.toString()
            val info: MutableList<Info>? = viewModel.listOfInfo.value

            val newNote: NoteEntry =
                NoteEntry(
                    title = title,
                    description = desc
                )

            val intent = Intent()
            intent.putExtra(NEW_ENTRY,
                EntryParcelableFactory.getEntryParcelable(newNote))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
