package tpu.mav26.catgame.ui.conponents

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsElement(
    title: String,
    currentValue: Int,
    onValueChanged: (Int) -> Unit
) {
    val btnColors = IconButtonColors(
        contentColor = Color.Black,
        containerColor = Color.Green,
        disabledContainerColor = Color.LightGray,
        disabledContentColor = Color.DarkGray
    )
    val numbers = listOf(1, 2, 3, 4, 5)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 32.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                colors = btnColors,
                modifier = Modifier.size(48.dp),
                onClick = { onValueChanged(if (currentValue - 1 == 0) currentValue else currentValue - 1) }
            ) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "minus")
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    numbers.forEachIndexed { index, number ->
                        AnimatedNumber(
                            number = number,
                            isSelected = numbers[index] == currentValue
                        )
                    }
                }
            }

            IconButton(
                colors = btnColors,
                modifier = Modifier.size(48.dp),
                onClick = { onValueChanged(if (currentValue + 1 > 5) currentValue else currentValue + 1) }
            ) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "add")
            }
        }
    }
}

@Composable
fun AnimatedNumber(
    number: Int,
    isSelected: Boolean
) {
    val offset by animateDpAsState(
        targetValue = if (isSelected) -10.dp else 0.dp,
        animationSpec = tween(durationMillis = 500), label = "pos"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .offset(y = offset)
            .background(
                color = if (isSelected) Color.Green else Color.Gray,
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        Text(
            text = number.toString(),
            color = if (isSelected) Color.Black else Color.White,
            fontSize = 24.sp
        )
    }
}