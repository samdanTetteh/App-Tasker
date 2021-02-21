package com.ijikod.apptasker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val appBarConfiguration : AppBarConfiguration by lazy {
        AppBarConfiguration(navHostFragment.navController.graph)
    }


    private var menuLayout: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup tool bar with navigation component
        toolBar.setupWithNavController(navHostFragment.navController, appBarConfiguration)
    }


    override fun onResume() {
        super.onResume()

        // Change menu items in action bar based on navigation destination
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            menuLayout = when(destination.id){
                R.id.addTaskFragment -> R.menu.save_task_menu
                else -> null
            }
            invalidateOptionsMenu()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuLayout?.let { menuInflater.inflate(it, menu) }
        return true
    }
}