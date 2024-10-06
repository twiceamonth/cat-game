package tpu.mav26.catgame.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {

    @Query("SELECT * FROM Settings")
    fun getSettings(): Settings?

    @Query("SELECT * FROM ScoreRowItem ORDER BY id DESC LIMIT 10")
    fun getScore(): List<ScoreRowItem>

    @Insert
    fun setSettings(settings: Settings)

    @Update
    fun updateSettings(settings: Settings)

    @Insert
    fun addNewScore(score: ScoreRowItem)

}