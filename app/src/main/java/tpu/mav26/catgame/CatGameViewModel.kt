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
import tpu.mav26.catgame.data.model.GameState
import tpu.mav26.catgame.data.model.Mouse
import kotlin.random.Random

class CatGameViewModel(context: Context) : ViewModel() {
    private val db = Room.databaseBuilder(
        context,
        CatGameDataBase::class.java,
        "cat_game_db"
    ).build()

    /*              States              */

    private val _settingsState = MutableStateFlow(Settings())
    val settingsState: StateFlow<Settings> = _settingsState

    private val _scoreState = MutableStateFlow<List<ScoreRowItem>?>(null)
    val scoreState: StateFlow<List<ScoreRowItem>?> = _scoreState

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _settingsState.value = db.dao().getSettings()
            _scoreState.value = db.dao().getScore()
        }
        // при первом заупуске приложния чтобы были мышки
        changeMouseCount(_settingsState.value.mouseCount)
    }

    /*              Settings                */

    fun changeMouseSize(newVal: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _settingsState.value = _settingsState.value.copy(
                mouseSize = newVal
            )
            db.dao().updateSettings(_settingsState.value)

            _gameState.value.mouseList.forEach {
                it.size = Consts.baseMouseSize * newVal
            }
        }
    }

    fun changeMouseCount(newVal: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            while (newVal > _gameState.value.mouseList.size ) {
                _gameState.value = _gameState.value.copy(
                    mouseList = _gameState.value.mouseList.plus(
                        Mouse(
                            Consts.baseMouseSize,
                            R.drawable.cat_splash
                        )
                    )
                )
            }

            if (newVal < _settingsState.value.mouseCount) {
                while (_gameState.value.mouseList.size > newVal) {
                    _gameState.value = _gameState.value.copy(
                        mouseList = _gameState.value.mouseList.minus(
                            _gameState.value.mouseList[Random.nextInt(
                                0,
                                _gameState.value.mouseList.size
                            )]
                        )
                    )
                }
            }

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

    /*              Game Process                */

    fun onExitSaveScore() {
        viewModelScope.launch(Dispatchers.IO) {
            val hetPerc =
                if (_gameState.value.hitClicks == 0) 0
                else (_gameState.value.hitClicks.toDouble() / _gameState.value.allClicks) * 100

            val newScoreItem = ScoreRowItem(
                allClicks = _gameState.value.allClicks,
                hitClicks = _gameState.value.hitClicks,
                hitPercentage = hetPerc.toInt()
            )
            db.dao().addNewScore(newScoreItem)
            _scoreState.value = _scoreState.value?.plus(newScoreItem)
            _scoreState.value = db.dao().getScore()
            _gameState.value = _gameState.value.copy(
                allClicks = 0,
                hitClicks = 0,
                mouseList = _gameState.value.mouseList
            )
        }
    }

    fun plusClick() {
        _gameState.value = _gameState.value.copy(
            allClicks = _gameState.value.allClicks + 1
        )
    }

    fun plusHitClick() {
        _gameState.value = _gameState.value.copy(
            hitClicks = _gameState.value.hitClicks + 1
        )
    }
}