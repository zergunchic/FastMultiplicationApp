package evs.factory.fastmultiplicationapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import evs.factory.fastmultiplicationapp.R
import evs.factory.fastmultiplicationapp.databinding.FragmentGameFinishedBinding
import evs.factory.fastmultiplicationapp.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private lateinit var gameResults:GameResult
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding?:throw RuntimeException("Binding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let{
            gameResults = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                retryGame()
            }
        })
        binding.button7.setOnClickListener{
            retryGame()
        }
        with(binding){
            textView10.text = String.format(
                getString(R.string.fin_right),
                gameResults.gameSettings.minCountOfRightAnswers
            )
            textView11.text = String.format(
                getString(R.string.fin_score),
                gameResults.countOfRightAnswers
            )
            textView12.text = String.format(
                getString(R.string.fin_req),
                gameResults.gameSettings.minPercentOfRightAnswers
            )
            textView13.text = String.format(
                getString(R.string.fin_perc),
                "fck u"
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun retryGame(){
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    companion object{
        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult):GameFinishedFragment{
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}