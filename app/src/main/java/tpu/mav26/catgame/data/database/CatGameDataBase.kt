package tpu.mav26.catgame.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Settings::class, ScoreRowItem::class], version = 1)
abstract class CatGameDataBase: RoomDatabase() {
    abstract fun dao(): Dao
}