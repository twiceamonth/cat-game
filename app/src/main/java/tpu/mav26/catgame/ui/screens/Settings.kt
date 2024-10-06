package tpu.mav26.catgame.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tpu.mav26.catgame.CatGameViewModel
import tpu.mav26.catgame.ui.conponents.SettingsElement

@Composable
fun Settings(
    viewModel: CatGameViewModel,
    modifier: Modifier = Modifier
) {
    val settingsState by viewModel.settingsState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Настройки",
            style = TextStyle(
                fontSize = 48.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 69.sp,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(40.dp))

        SettingsElement(
            title = "Размер мышки",
            currentValue = settingsState.mouseSize,
            onValueChanged = { viewModel.changeMouseSize(it) }
        )

        Spacer(modifier = Modifier.height(40.dp))

        SettingsElement(
            title = "Количество мышек",
            currentValue = settingsState.mouseCount,
            onValueChanged = { viewModel.changeMouseCount(it) }
        )

        Spacer(modifier = Modifier.height(40.dp))

        SettingsElement(
            title = "Скорость мышек",
            currentValue = settingsState.mouseSpeed,
            onValueChanged = { viewModel.changeMouseSpeed(it) }
        )
    }
}