package tpu.mav26.catgame.ui.conponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScoreListHeader() {
    val textStyle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center
    )

    Row(
        modifier = Modifier
            .height(48.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.1f)
                .border(
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(topStart = 15.dp)
                ),
        ) {
            Text(
                text = "#",
                style = textStyle
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(border = BorderStroke(1.dp, Color.Black)),
        ) {
            Text(
                text = "нажатия",
                style = textStyle
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(topEnd = 15.dp)
                ),
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("попадания\n")
                        withStyle(
                            SpanStyle(
                                baselineShift = BaselineShift.None, fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Gray
                            )
                        ) {
                            append("проценет попадания")
                        }
                    },
                    style = textStyle
                )
            }
        }
    }
}