package shvyn22.flexingfreegames.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.presentation.bookmarks.BookmarksScreen
import shvyn22.flexingfreegames.presentation.browse.BrowseScreen
import shvyn22.flexingfreegames.presentation.details.DetailsIntent
import shvyn22.flexingfreegames.presentation.details.DetailsScreen
import shvyn22.flexingfreegames.presentation.details.DetailsViewModel
import shvyn22.flexingfreegames.util.ResourceError

enum class Screen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    Browse("browse", R.string.nav_browse, R.drawable.ic_browse),
    Bookmarks("bookmarks", R.string.nav_bookmarks, R.drawable.ic_not_bookmarked),
    Details("details/{id}", R.string.nav_details, R.drawable.ic_web)
}

@ExperimentalPagerApi
@Composable
fun NavigationConfig(
    navController: NavHostController,
    onShowError: (ResourceError) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Browse.route
    ) {
        composable(Screen.Browse.route) {
            BrowseScreen(
                onShowError = onShowError,
                onNavigateToDetails = {
                    navController.navigate(
                        Screen.Details.route.replace("{id}", it.toString())
                    )
                },
                modifier = modifier
            )
        }

        composable(Screen.Bookmarks.route) {
            BookmarksScreen(
                onShowError = onShowError,
                onNavigateToDetails = {
                    navController.navigate(
                        Screen.Details.route.replace("{id}", it.toString())
                    )
                },
                modifier = modifier
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            it.arguments?.let { args ->
                val id = args.getInt("id")
                DetailsScreen(
                    id = id,
                    onShowError = onShowError,
                    modifier = modifier,
                )
            }
        }
    }
}