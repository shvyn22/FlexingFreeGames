package shvyn22.flexingfreegames.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

fun withItemCount(
    expectedCount: Int
) = object : TypeSafeMatcher<View>() {

    private var resourceName: String = ""

    override fun describeTo(description: Description?) {
        description?.appendText("with item count: $expectedCount")
        if (resourceName.isNotEmpty()) {
            description?.appendText("[$resourceName]")
        }
    }

    override fun matchesSafely(item: View?): Boolean {
        val actualCount: Int? = when (item) {
            is RecyclerView -> item.adapter?.itemCount
            is ViewPager2 -> item.adapter?.itemCount
            else -> return false
        }
        return actualCount == expectedCount
    }
}