package tpu.mav26.catgame.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Settings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var mouseSize: Int = 2,
    var mouseCount: Int = 3,
    var mouseSpeed: Int = 3
)

@Entity
data class ScoreRowItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val allClicks: Int,
    val hitClicks: Int,
    val hitPercentage: Int,
)