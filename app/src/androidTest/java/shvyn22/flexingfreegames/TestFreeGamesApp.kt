package shvyn22.flexingfreegames

import android.app.Application
import shvyn22.flexingfreegames.di.component.DaggerTestSingletonComponent
import shvyn22.flexingfreegames.di.component.SingletonComponent

class TestFreeGamesApp: Application() {

    lateinit var singletonComponent: SingletonComponent

    override fun onCreate() {
        super.onCreate()
        singletonComponent = DaggerTestSingletonComponent.factory().create(this)
    }
}