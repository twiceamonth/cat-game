package tpu.mav26.catgame.data.model

data class GameState(
    var allClicks: Int = 0,
    var hitClicks: Int = 0,
    var mouseList: List<Mouse> = mutableListOf()
)
