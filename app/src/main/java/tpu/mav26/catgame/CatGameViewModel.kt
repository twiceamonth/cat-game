package tpu.mav26.catgame

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tpu.mav26.catgame.data.database.CatGameDataBase
import tpu.mav26.catgame.data.database.ScoreRowItem
import tpu.mav26.catgame.data.database.Settings

class CatGameViewModel(context: Context): ViewModel() {
    private val db = Room.databaseBuilder(
        context,
        CatGameDataBase::class.java,
        "cat_game_db"
    ).build()

    private val _settingsState = MutableStateFlow(Settings())
    val settingsState: StateFlow<Settings> = _settingsState

    private val _scoreState = MutableStateFlow<List<ScoreRowItem>?>(null)
    val scoreState: StateFlow<List<ScoreRowItem>?> = _scoreState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _settingsState.value = db.dao().getSettings()
            _scoreState.value = db.dao().getScore()
        }
    }

    // TODO: подумать может можно 3 функции в 1 соеденить
    fun changeMouseSize(newVal: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _settingsState.value = _settingsState.value.copy(
                mouseSize = newVal
            )
            db.dao().updateSettings(_settingsState.value)
        }
    }

    fun changeMouseCount(newVal: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _settingsState.value = _settingsState.value.copy(
                mouseCount = newVal
            )
            db.dao().updateSettings(_settingsState.value)
        }
    }

    fun changeMouseSpeed(newVal: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _settingsState.value = _settingsState.value.copy(
                mouseSpeed = newVal
            )
            db.dao().updateSettings(_settingsState.value)
        }
    }

}