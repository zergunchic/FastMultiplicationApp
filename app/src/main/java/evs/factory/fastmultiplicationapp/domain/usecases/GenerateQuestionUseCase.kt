package evs.factory.fastmultiplicationapp.domain.usecases

import evs.factory.fastmultiplicationapp.domain.entity.Question
import evs.factory.fastmultiplicationapp.domain.repository.GameRepository

class GenerateQuestionUseCase (
    private val repository:GameRepository
        )
{
    operator fun invoke(maxSumValue: Int):Question{
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object{
        private const val COUNT_OF_OPTIONS = 6
    }
}
