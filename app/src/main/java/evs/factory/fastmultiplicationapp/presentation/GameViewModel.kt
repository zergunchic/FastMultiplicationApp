package evs.factory.fastmultiplicationapp.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import evs.factory.fastmultiplicationapp.R
import evs.factory.fastmultiplicationapp.data.GameRepositroyImpl
import evs.factory.fastmultiplicationapp.domain.entity.GameResult
import evs.factory.fastmultiplicationapp.domain.entity.GameSettings
import evs.factory.fastmultiplicationapp.domain.entity.Level
import evs.factory.fastmultiplicationapp.domain.entity.Question
import evs.factory.fastmultiplicationapp.domain.usecases.GenerateQuestionUseCase
import evs.factory.fastmultiplicationapp.domain.usecases.GetGameSettings

class GameViewModel(application: Application):AndroidViewModel(application) {
    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level
    private val context = application

    private val repository = GameRepositroyImpl()
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettings(repository)

    private lateinit var timer:CountDownTimer

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers:LiveData<Int>
        get() = _percentOfRightAnswers

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime : LiveData<String>
        get() =  _formattedTime

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers : LiveData<String>
        get() =  _progressAnswers

    private val _question = MutableLiveData<Question>()
    val question:LiveData<Question>
        get() = _question

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers:LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers:LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent:LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult:LiveData<GameResult>
        get() = _gameResult

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    fun startGame(level:Level){
        getGameSettings(level)
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int){
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress(){
        val percent = calculateProgress()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value =
            String.format(context.resources.getString(R.string.status_info),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers)
        _enoughCountOfRightAnswers.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value = percent>= gameSettings.minPercentOfRightAnswers
    }

    private fun calculateProgress():Int{
        if(countOfRightAnswers == 0) return 0
        return (countOfRightAnswers/countOfQuestions.toDouble()*100).toInt();
    }
    private fun checkAnswer(number:Int){
        val rightAnswer = question.value?.rightAnswer
        if(number == rightAnswer)
            countOfRightAnswers++
        countOfQuestions++
    }

    private fun getGameSettings(level:Level){
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer(){
        timer = object: CountDownTimer
            (gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
        MILLIS_IN_SECONDS){
            override fun onTick(p0: Long) {
                _formattedTime.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        timer.start()
    }

    private fun generateQuestion(){
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun formatTime(ms:Long):String{
        val seconds = ms/ MILLIS_IN_SECONDS
        val mins = seconds/60
        val leftSeconds = seconds - (mins*60)
        return String.format("%02d:%02d", mins, leftSeconds)
    }

    private fun finishGame(){
        _gameResult.value = GameResult(
            winner = enoughCountOfRightAnswers.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object{
        private const val MILLIS_IN_SECONDS = 1000L
    }

}