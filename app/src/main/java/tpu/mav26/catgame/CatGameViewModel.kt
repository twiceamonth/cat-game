package tpu.mav26.catgame

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
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

    private val stateMutex = Mutex()

    /*              States              */

    val mouseImageList = mutableListOf<Int>()
    val bcgImageList = mutableListOf<Int>()

    private val _settingsState = MutableStateFlow(Settings())
    val settingsState: StateFlow<Settings> = _settingsState

    private val _scoreState = MutableStateFlow<List<ScoreRowItem>?>(null)
    val scoreState: StateFlow<List<ScoreRowItem>?> = _scoreState

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState

    init {
        viewModelScope.launch (Dispatchers.IO) {
            _scoreState.value = db.dao().getScore()
        }

        for (i in 1..4) {
            mouseImageList.add(
                context.resources.getIdentifier(
                    "m_$i",
                    "drawable",
                    context.packageName
                )
            )
            bcgImageList.add(
                context.resources.getIdentifier(
                    "b_$i",
                    "drawable",
                    context.packageName
                )
            )
        }

        viewModelScope.launch {
            // при первом запуске записывает созданный стейт в таблицу,
            // при последующих запусках перезаписывает в стейт сохраненные настройки из таблицы
            stateMutex.withLock {
                withContext(Dispatchers.IO) {
                    val settings = db.dao().getSettings()
                    if (settings == null) {
                        db.dao().setSettings(_settingsState.value)
                        return@withContext
                    }
                    _settingsState.update { settings }
                }
            }

            // при первом заупуске приложния чтобы были мышки
            changeMouseCount(_settingsState.value.mouseCount)
        }
    }

    /*              Settings                */

    fun changeMouseSize(newVal: Int) {
        viewModelScope.launch {
            withContext((Dispatchers.IO)) {
                _settingsState.update { it.copy(mouseSize = newVal) }
                db.dao().updateSettings(_settingsState.value)
            }

            _gameState.value.mouseList.forEach {
                it.size = when(newVal) {
                    1 -> Consts.baseMouseSize * newVal // 50
                    2 -> Consts.baseMouseSize * newVal - 40 // 60
                    3 -> Consts.baseMouseSize * newVal - 80 // 70
                    4 -> Consts.baseMouseSize * newVal - 120 // 80
                    5 -> Consts.baseMouseSize * newVal - 160 // 90
                    else -> Consts.baseMouseSize
                }
            }
        }
    }

    fun changeMouseCount(newVal: Int) {
        viewModelScope.launch {
            stateMutex.withLock {
                while (newVal > _gameState.value.mouseList.size) {
                    _gameState.value = _gameState.value.copy(
                        mouseList = _gameState.value.mouseList.plus(
                            Mouse(
                                Consts.baseMouseSize,
                                mouseImageList[Random.nextInt(0, 3)]
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

                _settingsState.update { it.copy(mouseCount = newVal) }
                withContext(Dispatchers.IO) {
                    db.dao().updateSettings(_settingsState.value)
                }
            }
        }
    }

    fun changeMouseSpeed(newVal: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _settingsState.update { it.copy(mouseSpeed = newVal) }
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
            hitClicks = _gameState.value.hitClicks + 1,
        )
    }
}