package io.github.wertylop5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.get
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class GameMainActivity : AppCompatActivity(), EntryListFragment.OnEntryClickListener,
    EntryListFragment.OnCreateFragListener{
    private lateinit var entryList: MutableList<Entry>
    private var TAG: String = javaClass.simpleName

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
            val intent = Intent(this, CreateNoteActivity::class.java)
            startActivity(intent)
        }



        var db = AppDatabase.getInstance(applicationContext)
        var dao = db.noteEntryDao()

        var data = dao.getAll()
        if (data.isEmpty()) {

            //create test data
            entryList = ArrayList()
            for (x in 0..20) {
                entryList.add(NoteEntry(
                    title="big title",
                    description = "Test$x Test$x Test$x Test$x Test$x Test$x Test$x Test$x Test$x Test$x Test$x"))
            }

            dao.insertNoteEntries(entryList)
        }


        //add test data to db if it doesn't already exist


        /*
        TODO figure out how to initilize the fragment with data, maybe have the fragment
            implement either Serializable or Parcelable
            https://developer.android.com/guide/navigation/navigation-pass-data
         */
        //add the data to the fragments
        //val entryListFrag = supportFragmentManager.findFragmentById(R.id.entry_list_fragment) as? EntryListFragment
        //val entryListFrag = navController.graph.get(R.id.entry_list_fragment)
        //Log.d(TAG, entryListFrag.toString())
        //entryListFrag?.initEntryList(entryList)
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

    override fun onCreateFrag() {
        val frag = supportFragmentManager.findFragmentById(R.id.entry_list_fragment)
        Log.d(TAG, frag.toString())
    }
}
