package tpu.mav26.catgame.ui.screens

import android.content.Context
import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Animation
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tpu.mav26.catgame.CatGameViewModel
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun Game(
    viewModel: CatGameViewModel,
    modifier: Modifier = Modifier,
    onGoHome: () -> Unit
) {
    val wm = LocalContext.current.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val screenHeight = wm.currentWindowMetrics.bounds.height() - 500
    val screenWidth = wm.currentWindowMetrics.bounds.width() - 400

    val gameState by viewModel.gameState.collectAsState()
    val settingsState by viewModel.settingsState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    var isShowDialog by remember { mutableStateOf(false) }

    val animatedOffsets =
        remember { mutableStateListOf<Pair<Animatable<Float, *>, Animatable<Float, *>>>() }

    LaunchedEffect(Unit) {
        while (animatedOffsets.size < gameState.mouseList.size) {
            val newX = Random.nextInt(0, screenWidth).toFloat()
            val newY = Random.nextInt(0, screenHeight).toFloat()
            animatedOffsets.add(
                Animatable(newX) to Animatable(newY)
            )
        }
    }

    LaunchedEffect(settingsState.mouseSpeed) {
        animatedOffsets.forEachIndexed { _, (animX, animY) ->
            coroutineScope.launch {
                startAnimation(animX, animY, settingsState.mouseSpeed, screenWidth, screenHeight)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { viewModel.plusClick() }
            )
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

        gameState.mouseList.forEach { mouse ->
            animatedOffsets.forEachIndexed { index, (animX, animY) ->
                Image(
                    painter = painterResource(id = mouse.img),
                    contentDescription = "mouse",
                    modifier = Modifier
                        .size((mouse.size * settingsState.mouseSize).dp)
                        .offset {
                            IntOffset(
                                x = animX.value.toInt(),
                                y = animY.value.toInt()
                            )
                        }
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                viewModel.plusHitClick()
                                viewModel.plusClick()

                                val newX = Random
                                    .nextInt(0, screenWidth)
                                    .toFloat()
                                val newY = Random
                                    .nextInt(0, screenHeight)
                                    .toFloat()
                                coroutineScope.launch {
                                    animatedOffsets[index].first.snapTo(newX)
                                    animatedOffsets[index].second.snapTo(newY)
                                    startAnimation(
                                        animX,
                                        animY,
                                        settingsState.mouseSpeed,
                                        screenWidth,
                                        screenHeight
                                    )
                                }
                            }
                        )
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Text(text = "${gameState.hitClicks}/${gameState.allClicks}")
        }
    }
}

suspend fun startAnimation(
    animX: Animatable<Float, *>,
    animY: Animatable<Float, *>,
    animationSpeedModifier: Int,
    screenWidth: Int,
    screenHeight: Int
) {
    while (true) {
        val targetX = Random.nextInt(0, screenWidth).toFloat()
        val targetY = Random.nextInt(0, screenHeight).toFloat()

        animX.animateTo(
            targetValue = targetX,
            animationSpec = tween(
                durationMillis = 2000 / animationSpeedModifier,
                easing = LinearEasing
            )
        )
        animY.animateTo(
            targetValue = targetY,
            animationSpec = tween(
                durationMillis = 2000 / animationSpeedModifier,
                easing = LinearEasing
            )
        )
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
            text = {
                Text(
                    "Вы действительно хотите выйти в меню?" +
                            "\nНабранные очки будут сохранены!"
                )
            },
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