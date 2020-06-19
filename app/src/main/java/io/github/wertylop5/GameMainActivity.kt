package io.github.wertylop5

import android.content.Intent
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
import io.github.wertylop5.model.AppDatabase
import io.github.wertylop5.model.Entry
import io.github.wertylop5.model.EntryRepository
import io.github.wertylop5.model.NoteEntry
import kotlinx.coroutines.launch

class GameMainActivity : AppCompatActivity(), EntryListFragment.OnEntryClickListener {
    private lateinit var entryList: MutableList<Entry>
    private var TAG: String = javaClass.simpleName


    companion object {
        var EXISTING_ENTRY: String = "io.github.wertylop5.EXISTING_ENTRY"
    }

    //have to split it, otherwise packageName property is not accessible
//    init {
//        EXISTING_ENTRY = "$packageName.EXISTING_ENTRY"
//    }

    val addNewEntry = registerForActivityResult(CreateEntryContract()) {
        val repository: EntryRepository  = EntryRepository(
            AppDatabase.getInstance(this, lifecycleScope).noteEntryDao())

        it?.let {
            Log.d(TAG, "adding new entry to db")
            when (it) {
                is NoteEntry -> lifecycleScope.launch {
                    repository.insert(it)
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

        val fab: FloatingActionButton = findViewById(R.id.add_entry_button)
        fab.setOnClickListener { view ->
            //val intent = Intent(this, CreateEntryActivity::class.java)
            addNewEntry.launch(null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.menu_game_main, menu)

        return true
    }

    fun onGroupItemClick(item: MenuItem) {

    }

    override fun onEntryClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
