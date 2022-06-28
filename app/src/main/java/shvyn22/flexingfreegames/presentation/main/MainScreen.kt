package shvyn22.flexingfreegames.presentation.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.presentation.ui.components.*
import shvyn22.flexingfreegames.util.Orientation
import shvyn22.flexingfreegames.util.ResourceError
import shvyn22.flexingfreegames.util.rememberOrientation

@ExperimentalPagerApi
@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val navController = rememberNavController()
    val orientation = rememberOrientation()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currRoute = navBackStackEntry?.destination?.route

    val navItems = listOf(Screen.Browse, Screen.Bookmarks)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = when (currRoute) {
                    Screen.Browse.route -> stringResource(id = Screen.Browse.title)
                    Screen.Bookmarks.route -> stringResource(id = Screen.Bookmarks.title)
                    Screen.Details.route -> stringResource(id = Screen.Details.title)
                    else -> ""
                },
                isNested = currRoute == Screen.Details.route,
                onBackPressed = { navController.popBackStack() }
            )
        },
        bottomBar = {
            if (orientation == Orientation.PORTRAIT) {
                BottomNavBar(
                    navController = navController,
                    items = navItems
                )
            }
        }
    ) { innerPadding ->
        Row(
            modifier = modifier
                .padding(innerPadding)
        ) {
            if (orientation == Orientation.LANDSCAPE) {
                NavRail(
                    navController = navController,
                    items = navItems
                )
            }

            NavigationConfig(
                navController = navController,
                onShowError = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = when (it) {
                                is ResourceError.Fetching ->
                                    context.getString(R.string.text_error_fetching)
                                is ResourceError.NoBookmarks ->
                                    context.getString(R.string.text_error_no_bookmarks)
                                is ResourceError.Specified -> it.msg
                            }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}