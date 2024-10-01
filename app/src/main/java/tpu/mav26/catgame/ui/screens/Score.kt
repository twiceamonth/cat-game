package tpu.mav26.catgame.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tpu.mav26.catgame.ui.conponents.ScoreListHeader
import tpu.mav26.catgame.ui.conponents.ScoreListItem

@Composable
fun Score(modifier: Modifier = Modifier) {
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

        LazyColumn {
            item { ScoreListHeader() }
            items(10) { index -> ScoreListItem(/*TODO: listItem*/ index) }
        }
    }
}