package shvyn22.flexingfreegames.util

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`

fun withItemCount(
    expectedCount: Int
) = ViewAssertion { view, noViewFoundException ->

    if (noViewFoundException != null) throw noViewFoundException

    val recyclerView = view as RecyclerView
    val adapter = recyclerView.adapter

    assertThat(adapter?.itemCount, `is`(expectedCount))
}