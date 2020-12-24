@file:Suppress("DEPRECATION")

package co.marcellino.moviecatalogue.ui.catalogue

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import co.marcellino.moviecatalogue.R
import co.marcellino.moviecatalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatalogueActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(CatalogueActivity::class.java)

    private val resources = ApplicationProvider.getApplicationContext<Context>().resources

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun showMoviesCatalogue() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20)
        )
    }

    @Test
    fun showMoviesDetails() {
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        onView(withId(R.id.tv_details_title)).check(matches(isDisplayed()))

        onView(withId(R.id.iv_details_poster)).check(matches(isDisplayed()))
    }

    @Test
    fun backPressMovieDetails() {
        showMoviesDetails()

        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
    }

    @Test
    fun showShowsCatalogue() {
        onView(withText(resources.getString(R.string.title_shows))).perform(click())
        onView(withId(R.id.rv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20)
        )
    }

    @Test
    fun showShowsDetails() {
        showShowsCatalogue()
        onView(withId(R.id.rv_shows)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
        )

        onView(withId(R.id.rv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        onView(withId(R.id.tv_details_title)).check(matches(isDisplayed()))

        onView(withId(R.id.iv_details_poster)).check(matches(isDisplayed()))
    }

    @Test
    fun backPressShowDetails() {
        showShowsDetails()

        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.rv_shows)).check(matches(isDisplayed()))
    }
}