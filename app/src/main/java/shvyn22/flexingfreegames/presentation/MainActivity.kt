package shvyn22.flexingfreegames.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import shvyn22.flexingfreegames.presentation.main.MainScreen
import shvyn22.flexingfreegames.presentation.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}