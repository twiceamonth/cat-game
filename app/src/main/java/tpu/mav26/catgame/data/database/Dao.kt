package tpu.mav26.catgame.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {

    @Query("SELECT * FROM Settings")
    fun getSettings(): Settings

    @Query("SELECT * FROM ScoreRowItem ORDER BY id LIMIT 10")
    fun getScore(): List<ScoreRowItem>

    @Update
    fun updateSettings(settings: Settings)

    @Insert
    fun addNewScore(score: ScoreRowItem)

}