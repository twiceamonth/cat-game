package tpu.mav26.catgame.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Game(
    modifier: Modifier = Modifier,
    onGoHome: () -> Unit
) {
    var hitedTaps by remember { mutableIntStateOf(0) }
    var allTaps by remember { mutableIntStateOf(0) }
    var isShowDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier
        .fillMaxSize()
        .clickable { allTaps++ }
    ) {
        ExitDialog(
            isShowDialog = isShowDialog,
            onDialogStateChange = { isShowDialog = it },
            onGoHome = onGoHome
        )

        IconButton(
            onClick = { isShowDialog = !isShowDialog }, modifier = modifier
                .align(Alignment.TopEnd)
                .size(48.dp)
        ) {
            Icon(Icons.Default.Home, contentDescription = "")
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(text = "$hitedTaps/$allTaps")
        }
    }
}

@Composable
fun ExitDialog(
    isShowDialog: Boolean,
    onDialogStateChange: (Boolean) -> Unit,
    onGoHome: () -> Unit
) {
    if (isShowDialog) {
        AlertDialog(
            title = { Text("Выход в меню") },
            text = { Text("Вы действительно хотите выйти в меню?") },
            onDismissRequest = { },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDialogStateChange(!isShowDialog)
                        onGoHome()
                    }
                ) {
                    Text(text = "Да")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDialogStateChange(!isShowDialog) }) {
                    Text(text = "Нет")
                }
            }
        )
    }
}