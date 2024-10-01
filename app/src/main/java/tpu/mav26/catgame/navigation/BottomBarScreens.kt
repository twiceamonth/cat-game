package tpu.mav26.catgame.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreens(val title: String, val icon: ImageVector, val route: String) {
    data object Settings: BottomBarScreens("Настройки", icon = Icons.Default.Settings, route = Routes.CAT_GAME_SETTINGS)
    data object Main: BottomBarScreens("Играть", icon = Icons.Default.Home, route = Routes.CAT_GAME_MAIN)
    data object Score: BottomBarScreens("Счет", icon = Icons.Default.Menu, route = Routes.CAT_GAME_SCORE)
}