package evs.factory.fastmultiplicationapp.domain.repository

import evs.factory.fastmultiplicationapp.domain.entity.GameSettings
import evs.factory.fastmultiplicationapp.domain.entity.Level
import evs.factory.fastmultiplicationapp.domain.entity.Question

interface GameRepository{
    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions:Int
    ):Question
    fun getGameSettings(level: Level): GameSettings
}