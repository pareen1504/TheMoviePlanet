package com.example.moviedb.ui.navigation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.example.moviedb.ui.screen.favoritemovie.FavoriteMovieFragment

/**
 * https://github.com/STAR-ZERO/navigation-keep-fragment-sample/blob/master/app/src/main/java/com/star_zero/navigation_keep_fragment_sample/navigation/KeepStateNavigator.kt
 */

@Navigator.Name("keep_state_fragment") // `keep_state_fragment` is used in navigation xml
class KeepStateNavigator(
    private val context: Context,
    private val fragmentManager: FragmentManager, // Should pass childFragmentManager.
    private val containerId: Int
) : FragmentNavigator(context, fragmentManager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString()
        val transaction = fragmentManager.beginTransaction()

        val currentFragment = fragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        var fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            val className = destination.className
            fragment = instantiateFragment(context, fragmentManager, className, args)
            transaction.add(containerId, fragment, tag)
        } else {
            // reload favorite movie
            if (fragment is FavoriteMovieFragment) {
                fragment.loadData()
            }

            transaction.show(fragment)
        }

        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commit()

        return destination
    }
}
