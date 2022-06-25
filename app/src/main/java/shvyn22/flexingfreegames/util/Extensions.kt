package shvyn22.flexingfreegames.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import shvyn22.flexingfreegames.FreeGamesApp
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.di.component.SingletonComponent

fun RequestBuilder<Drawable>.defaultRequests(): RequestBuilder<Drawable> {
    return this
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.ic_error)
}

fun View.toggleVisibility(
    root: ViewGroup,
    gravity: Int = Gravity.TOP
) {
    val transition = Slide(gravity)
    transition.duration = 1000
    transition.addTarget(this)

    TransitionManager.beginDelayedTransition(root, transition)
    this.isVisible = !this.isVisible
}

val Context.singletonComponent: SingletonComponent
    get() = when (this) {
        is FreeGamesApp -> singletonComponent
        else -> applicationContext.singletonComponent
    }