package io.github.wertylop5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import io.github.wertylop5.model.*
import kotlinx.coroutines.launch

class GameMainActivity : AppCompatActivity(), EntryListFragment.OnEntryClickListener {
    private lateinit var entryList: MutableList<Entry>
    private var TAG: String = javaClass.simpleName

    companion object {
        var EXISTING_ENTRY: String = "io.github.wertylop5.EXISTING_ENTRY"
    }

    private val addNewEntry = registerForActivityResult(CreateEntryContract()) {
        //not saving this as a property so that it doesn't depend on activity state
        val entryRepository = EntryRepository(
            AppDatabase.getInstance(this, lifecycleScope).noteEntryDao())
        val infoRepository = InfoRepository(
            AppDatabase.getInstance(this, lifecycleScope).infoDao()
        )

        it?.let {
            Log.d(TAG, "adding new entry to db")
            when (it) {
                is NoteEntry -> lifecycleScope.launch {
                    entryRepository.insertNoteEntry(it)
                }
                is NoteEntryWithInfo -> lifecycleScope.launch {
                    val rowid = entryRepository.insertNoteEntry(it.noteEntry)

                    Log.d(TAG, rowid.toString())

                    //now get the new id for the NoteEntry
                    val newNoteEntry = entryRepository.getNoteEntryByRowid(rowid)

                    it.info.forEach {
                        it.noteId = newNoteEntry.noteEntryId
                    }

                    infoRepository.insertInfoItems(it.info)
                }
                else -> null
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        //allow the Toolbar to be updated when navigation destination changes
        val navController: NavController = findNavController(R.id.nav_host_fragment)
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController,
                findViewById<DrawerLayout>(R.id.game_main_drawer_layout))

        findViewById<NavigationView>(R.id.navigation_view)
            .setupWithNavController(navController)

        val speedDialView = findViewById<SpeedDialView>(R.id.speedDial)
        speedDialView.addActionItem(
            //first id is just an id resource, second id is the icon to use
            SpeedDialActionItem.Builder(R.id.fab_add_note_entry, R.drawable.ic_baseline_add_24)
                .setLabel(getString(R.string.fab_label_add_note_entry))
                .create())
        speedDialView.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.fab_add_note_entry -> {
                    addNewEntry.launch(null)
                    speedDialView.close() // To close the Speed Dial with animation
                    return@OnActionSelectedListener true // false will close it without animation
                }
            }
            false
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.menu_game_main, menu)

        return true
    }

    fun onGroupItemClick(item: MenuItem) {

    }

    override fun onEntryClick(entry: Entry?) {
        Log.d(TAG, "main activity got $entry")
        addNewEntry.launch(entry)
    }
}
