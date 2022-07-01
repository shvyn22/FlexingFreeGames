package shvyn22.flexingfreegames

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class DaggerTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, TestFreeGamesApp::class.java.name, context)
    }
}