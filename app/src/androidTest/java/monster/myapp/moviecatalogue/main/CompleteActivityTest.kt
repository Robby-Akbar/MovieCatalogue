package monster.myapp.moviecatalogue.main

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by robby on 06/05/21.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class CompleteActivityTest {

    /*@Before
    fun setup() {
        ActivityScenario.launch(SplashActivity::class.java)
    }*/

    @Test
    fun completeActivityTest() {
        /*onView(allOf(isDisplayed(), withId(R.id.recyclerview)))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
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
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
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
        )*/
    }
}