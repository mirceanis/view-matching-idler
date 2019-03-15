package ro.mirceanistor.sampleapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import ro.mirceanistor.testutil.ViewMatcherIdler

/**
 * It's easy to mark a view in the hierarchy as a progress indicator,
 * forcing espresso to wait until it is done.
 */
class ExampleTestUsingResource {

    @get:Rule
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun clickTheButtonAndWaitUsingResource() {
        onView(withId(R.id.btn_start_operation)).perform(click())

        //This will keep espresso busy while at least one view matches this matcher
        val idler = ViewMatcherIdler(allOf(withText("loading"), isDisplayed()))
        IdlingRegistry.getInstance().register(idler)

        onView(withText("all done")).check(matches(isDisplayed()))

        IdlingRegistry.getInstance().unregister(idler)
    }

}
