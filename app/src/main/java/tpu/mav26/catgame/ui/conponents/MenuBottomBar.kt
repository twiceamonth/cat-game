package tpu.mav26.catgame.ui.conponents

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import tpu.mav26.catgame.navigation.BottomBarScreens

@Composable
fun MenuBottomBar(
    selectedScreen: Int,
    items: List<BottomBarScreens>,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = selectedScreen == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}
