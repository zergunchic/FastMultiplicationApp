package evs.factory.fastmultiplicationapp.data

import evs.factory.fastmultiplicationapp.domain.entity.GameSettings
import evs.factory.fastmultiplicationapp.domain.entity.Level
import evs.factory.fastmultiplicationapp.domain.entity.Question
import evs.factory.fastmultiplicationapp.domain.repository.GameRepository
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.random.Random

class GameRepositroyImpl: GameRepository {
    private val MIN_SUM_VAL = 2
    private val MIN_ANSWER_VAL = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VAL, maxSumValue+1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VAL, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max( rightAnswer - countOfOptions, MIN_ANSWER_VAL)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while(options.size < countOfOptions){
            options.add(Random.nextInt(from,to))
        }

        return Question(sum,visibleNumber, options.toList());
    }

    override fun getGameSettings(level: Level): GameSettings {
       return when(level){
           Level.TEST -> GameSettings(10, 3, 50, 8)
           Level.EASY -> GameSettings(10, 10, 70, 60)
           Level.NORMAL -> GameSettings(20, 20, 80, 40)
           Level.HARD -> GameSettings(30, 25, 90, 40)
       }
    }
}