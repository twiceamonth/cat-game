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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tpu.mav26.catgame.data.database.ScoreRowItem

@Composable
fun ScoreListItem(item: ScoreRowItem, index: Int) {
    val textStyle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal
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
                    shape = if (index == 9) RoundedCornerShape(bottomStart = 15.dp)
                            else RoundedCornerShape(0.dp)
                ),
        ) {
            Text(
                text = (index + 1).toString(),
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
                text = item.allClicks.toString(),
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
                    shape = if (index == 9) RoundedCornerShape(bottomEnd = 15.dp)
                            else RoundedCornerShape(0.dp)
                ),
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(item.hitClicks.toString() + " ")
                        withStyle(
                            SpanStyle(
                                baselineShift = BaselineShift.None, fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Gray
                            )
                        ) {
                            append(item.hitPercentage.toString() + "%")
                        }
                    },
                    style = textStyle
                )
            }
        }
    }
}