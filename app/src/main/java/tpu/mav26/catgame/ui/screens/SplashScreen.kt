package tpu.mav26.catgame.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import tpu.mav26.catgame.R
import tpu.mav26.catgame.navigation.Routes

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = androidx.compose.animation.core.tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(3000L)
        navController.navigate(Routes.CAT_GAME_HOME) {
            popUpTo(Routes.CAT_GAME_SPLASH) {
                inclusive = true
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.cat_splash),
                contentDescription = "splash"
            )
            Text(text = "Cat Game", fontSize = 24.sp)
        }
    }
}