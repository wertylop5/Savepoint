package io.github.wertylop5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CreateNoteActivity : AppCompatActivity() {
    private val TAG: String = CreateNoteActivity::class.java.name

    private lateinit var titleText: EditText
    private lateinit var descText: EditText

    private lateinit var infoAdapter: InfoAdapter
    private lateinit var infoRecyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: CreateNoteViewModel

    private lateinit var defaultKeyString: String
    private lateinit var defaultValueString: String

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

        viewModel = ViewModelProvider(this).get(CreateNoteViewModel::class.java)
        viewModel.listOfInfo.observe(this, Observer {
            it?.let {
                Log.d(TAG, it.size.toString())
                infoAdapter.setInfoElems(it)
            }
        })

        defaultKeyString = getString(R.string.default_info_key)
        defaultValueString = getString(R.string.default_info_value)

        findViewById<Button>(R.id.note_add_info_button).setOnClickListener {
            viewModel.insert(Info(key = defaultKeyString, value = defaultValueString))
        }

        findViewById<Button>(R.id.note_create_button).setOnClickListener {
            Toast.makeText(applicationContext, "nice", Toast.LENGTH_SHORT).show()
        }
    }
}
