package com.droidafricana.moveery

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.toolbar
import javax.inject.Inject

/**
 * The [MainActivity] is only responsible for setting up navigation and injection
 */
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val destinationsList = listOf(
        R.id.dest_movie_details_fragment,
        R.id.dest_movie_search_fragment,
        R.id.dest_shows_search_fragment,
        R.id.dest_show_details_fragment,
        R.id.dest_similar_movie_fragment,
        R.id.dest_similar_show_fragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val host = supportFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment? ?: return

        navController = host.navController

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dest_movies_landing_fragment, R.id.dest_shows_landing_fragment,
                R.id.dest_favourite_movies_fragment, R.id.dest_favourite_shows_fragment
            ),
            drawerLayout
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in destinationsList) {
                //Hide the toolbar
                toolbar.visibility = View.GONE
            } else toolbar.visibility = View.VISIBLE
        }

        setupActionBar(navController, appBarConfiguration)
        setupNavigationMenu(navController)
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        // This allows NavigationUI to decide what label to show in the action bar
        // By using appBarConfig, it will also determine whether to
        // show the up arrow or drawer menu icon
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun setupNavigationMenu(navController: NavController) {
        val sideNavView = findViewById<NavigationView>(R.id.main_nav_view)
        sideNavView?.setupWithNavController(navController)
        sideNavView.setCheckedItem(R.id.dest_movies_landing_fragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return navController.navigateUp(appBarConfiguration)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
}
