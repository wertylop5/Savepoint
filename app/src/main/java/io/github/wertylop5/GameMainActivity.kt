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

class GameMainActivity : AppCompatActivity(), EntryListFragment.OnEntryClickListener {
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
