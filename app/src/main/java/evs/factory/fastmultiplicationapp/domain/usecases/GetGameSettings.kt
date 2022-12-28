package evs.factory.fastmultiplicationapp.domain.usecases

import evs.factory.fastmultiplicationapp.domain.entity.GameSettings
import evs.factory.fastmultiplicationapp.domain.entity.Level
import evs.factory.fastmultiplicationapp.domain.repository.GameRepository

class GetGameSettings(
    private val repository: GameRepository
){
    operator fun invoke(level:Level):GameSettings{
        return repository.getGameSettings(level)
    }
}