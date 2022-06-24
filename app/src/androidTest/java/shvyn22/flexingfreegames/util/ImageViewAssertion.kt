package shvyn22.flexingfreegames.util

import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

fun withImageDrawable(
    expectedId: Int
) = object : TypeSafeMatcher<View>() {

    private var resourceName: String = ""

    override fun describeTo(description: Description?) {
        description?.appendText("with drawable from resource id: $expectedId")
        if (resourceName.isNotEmpty()) {
            description?.appendText("[$resourceName]")
        }
    }

    override fun matchesSafely(item: View?): Boolean {
        if (item !is ImageView) return false

        val resources = item.context.resources
        val drawable = resources.getDrawable(expectedId, item.context.theme)
        resourceName = resources.getResourceName(expectedId)

        if (drawable == null) return false

        val bitmap = item.drawable.toBitmap()
        val expectedBitmap = drawable.toBitmap()

        return bitmap.sameAs(expectedBitmap)
    }
}