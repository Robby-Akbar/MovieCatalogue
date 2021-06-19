package monster.myapp.moviecatalogue.favorite

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import monster.myapp.moviecatalogue.R

/**
 * Created by robby on 13/05/21.
 */
class SectionsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.title_movie, R.string.title_tv_show)
    }

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteCatalogueFragment("movie")
            1 -> FavoriteCatalogueFragment("tvshow")
            else -> Fragment()
        }
    }

}