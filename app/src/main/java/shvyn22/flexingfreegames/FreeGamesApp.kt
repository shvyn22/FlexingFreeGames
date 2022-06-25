package shvyn22.flexingfreegames

import android.app.Application
import shvyn22.flexingfreegames.di.component.DaggerSingletonComponent
import shvyn22.flexingfreegames.di.component.SingletonComponent

class FreeGamesApp : Application() {

    lateinit var singletonComponent: SingletonComponent

    override fun onCreate() {
        super.onCreate()

        singletonComponent = DaggerSingletonComponent.factory().create(this)
    }
}