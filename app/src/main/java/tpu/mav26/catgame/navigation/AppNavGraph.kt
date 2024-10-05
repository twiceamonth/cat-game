package tpu.mav26.catgame.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import tpu.mav26.catgame.CatGameViewModel
import tpu.mav26.catgame.Consts
import tpu.mav26.catgame.ui.conponents.MenuBottomBar
import tpu.mav26.catgame.ui.screens.CatGameMain
import tpu.mav26.catgame.ui.screens.Game
import tpu.mav26.catgame.ui.screens.Score
import tpu.mav26.catgame.ui.screens.Settings
import tpu.mav26.catgame.ui.screens.SplashScreen

@Composable
fun AppNavGraph(
    viewModel: CatGameViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Routes.CAT_GAME_SPLASH) {
        composable(Routes.CAT_GAME_SPLASH) {
            SplashScreen(navController = navController)
        }

        composable(Routes.CAT_GAME_GAME) {
            Game(
                modifier = modifier,
                onGoHome = {
                    navController.navigate(Routes.CAT_GAME_HOME) {
                        popUpTo(Routes.CAT_GAME_GAME) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        navigation(startDestination = Routes.CAT_GAME_MAIN, route = Routes.CAT_GAME_HOME) {
            composable(Routes.CAT_GAME_MAIN) {
                var selectedScreen by remember { mutableIntStateOf(1) }

                Scaffold(
                    bottomBar = {
                        MenuBottomBar(
                            selectedScreen = selectedScreen,
                            items = Consts.BottomBarItems,
                            onItemSelected = { selectedScreen = it }
                        )
                    }
                ) { innerPadding ->
                    when (Consts.BottomBarItems[selectedScreen].route) {
                        Routes.CAT_GAME_SETTINGS -> Settings(
                            viewModel = viewModel,
                            modifier = modifier.padding(
                                innerPadding
                            )
                        )

                        Routes.CAT_GAME_MAIN -> CatGameMain(
                            modifier = modifier.padding(innerPadding),
                            onStart = {
                                navController.navigate(Routes.CAT_GAME_GAME) {
                                    popUpTo(Routes.CAT_GAME_MAIN) {
                                        inclusive = true
                                    }
                                }
                            })

                        Routes.CAT_GAME_SCORE -> Score(
                            viewModel = viewModel,
                            modifier = modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}