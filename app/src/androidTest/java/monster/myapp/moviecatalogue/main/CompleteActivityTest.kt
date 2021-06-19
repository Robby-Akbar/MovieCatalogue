package monster.myapp.moviecatalogue.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import monster.myapp.moviecatalogue.R
import monster.myapp.moviecatalogue.core.utils.DataDummy
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by robby on 06/05/21.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class CompleteActivityTest {

    private val dummyMovie = DataDummy.generateDummyMovies()
    private val dummyTvShow = DataDummy.generateDummyTvShows()

    @Before
    fun setup() {
        ActivityScenario.launch(SplashActivity::class.java)
    }

    @Test
    fun completeActivityTest() {
        onView(allOf(isDisplayed(), withId(R.id.recyclerview)))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
            )
        onView(withId(R.id.tv_item_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).check(matches(isDisplayed())).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withContentDescription(R.string.title_tv_show))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(allOf(isDisplayed(), withId(R.id.recyclerview)))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShow.size))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
            )

        onView(withId(R.id.img_backdrop))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.app_bar)).perform(CustomViewActions().collapseAppBarLayout())
        onView(withContentDescription(R.string.abc_action_bar_up_description))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withContentDescription(R.string.txt_favored))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(withContentDescription(R.string.title_tv_show))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, CustomViewActions().clickChildViewWithId(R.id.btn_share)
            )
        )
    }
}