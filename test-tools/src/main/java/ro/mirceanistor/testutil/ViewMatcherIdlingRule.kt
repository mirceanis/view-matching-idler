package ro.mirceanistor.testutil

import android.support.test.espresso.IdlingRegistry
import android.view.View
import org.hamcrest.Matcher
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * A rule that registers an idling resource that is busy while the [viewMatcher] matches
 * at least one view.
 */
class ViewMatcherIdlingRule(private val viewMatcher: Matcher<View>) : TestRule {

    private class ViewMatchingStatement(
        private val baseStatement: Statement?,
        private val matcher: Matcher<View>
    ) : Statement() {

        override fun evaluate() {
            val idler = ViewMatcherIdler(matcher)
            IdlingRegistry.getInstance().register(idler)
            baseStatement?.evaluate()
            IdlingRegistry.getInstance().unregister(idler)
        }

    }

    override fun apply(base: Statement?, description: Description?): Statement {
        return ViewMatchingStatement(base, viewMatcher)
    }

}