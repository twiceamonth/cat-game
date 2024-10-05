package tpu.mav26.catgame.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tpu.mav26.catgame.CatGameViewModel
import tpu.mav26.catgame.ui.conponents.ScoreListHeader
import tpu.mav26.catgame.ui.conponents.ScoreListItem

@Composable
fun Score(
    viewModel: CatGameViewModel,
    modifier: Modifier = Modifier
) {
    val scoreState by viewModel.scoreState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Статистика",
            style = TextStyle(
                fontSize = 48.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 69.sp,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(40.dp))

        if (scoreState != null && scoreState!!.isNotEmpty()) {
            LazyColumn {
                item { ScoreListHeader() }
                items(scoreState!!.size) { index ->
                    ScoreListItem(
                        scoreState!![index],
                        index,
                        scoreState!!.size
                    )
                }
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(15.dp)
                    )
            ) {
                Text(
                    "Пока результатов нет.\nЗдесь будут отображены последнеи 10 игр.",
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    lineHeight = 52.sp
                )
            }
        }

    }
}