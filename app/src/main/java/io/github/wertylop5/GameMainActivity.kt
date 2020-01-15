package io.github.wertylop5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

class GameMainActivity : AppCompatActivity(), EntryListFragment.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        //allow the Toolbar to be updated when navigation destination changes
        val navController: NavController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController,
                findViewById<DrawerLayout>(R.id.game_main_drawer_layout))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.menu_game_main, menu)

        return true
    }

    fun onGroupItemClick(item: MenuItem) {

    }

    override fun onItemClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
