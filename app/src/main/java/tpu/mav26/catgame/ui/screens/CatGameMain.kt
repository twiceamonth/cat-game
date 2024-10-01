package tpu.mav26.catgame.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CatGameMain(modifier: Modifier = Modifier, onStart: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(128.dp))

            Text(
                text = "Cat\nGame",
                style = TextStyle(
                    fontSize = 96.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 69.sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = onStart,
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .height(75.dp)
                    .width(260.dp)
            ) {
                Text(
                    text = "Старт",
                    style = TextStyle(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Spacer(modifier = Modifier.height(112.dp))
        }
    }
}

