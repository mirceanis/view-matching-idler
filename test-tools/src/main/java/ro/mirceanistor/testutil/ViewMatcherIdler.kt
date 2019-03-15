package ro.mirceanistor.testutil

import android.app.Activity
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.IdlingResource.ResourceCallback
import android.support.test.espresso.util.TreeIterables
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.view.View
import org.hamcrest.Matcher

/**
 * keep busy while at least one view matches the matcher
 */
class ViewMatcherIdler(private val viewMatcher: Matcher<View>) : IdlingResource {

    override fun getName(): String = "view matching idling resource. " +
            "It will keep busy while at least one view matches the matcher: ViewMatcher($viewMatcher)"

    override fun isIdleNow(): Boolean {
        val currentActivity = getCurrentActivity()
        currentActivity?.let {
            val root = it.window.decorView
            val anyViewMatches = hasMatchingView(viewMatcher, root)
            if (anyViewMatches) {
                //busy while there's a matching view
                return false
            }
        }
        callback?.onTransitionToIdle()
        return true
    }

    private var callback: ResourceCallback? = null

    override fun registerIdleTransitionCallback(callback: ResourceCallback?) {
        this.callback = callback
    }

    private fun hasMatchingView(viewMatcher: Matcher<View>, root: View): Boolean {
        val viewIterable = TreeIterables.breadthFirstViewTraversal(root)
        val numMatchingViews = viewIterable.filter {
            viewMatcher.matches(it)
        }
        return numMatchingViews.isNotEmpty()
    }

    private fun getCurrentActivity(): Activity? {
        val activities = ActivityLifecycleMonitorRegistry
            .getInstance()
            .getActivitiesInStage(Stage.RESUMED)
        return activities.firstOrNull()
    }

}