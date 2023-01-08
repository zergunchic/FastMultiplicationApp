package evs.factory.fastmultiplicationapp.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import evs.factory.fastmultiplicationapp.R
import evs.factory.fastmultiplicationapp.databinding.FragmentGameBinding
import evs.factory.fastmultiplicationapp.databinding.FragmentGameFinishedBinding
import evs.factory.fastmultiplicationapp.domain.entity.GameResult
import evs.factory.fastmultiplicationapp.domain.entity.GameSettings
import evs.factory.fastmultiplicationapp.domain.entity.Level

class GameFragment : Fragment() {
    private lateinit var level:Level;
    private val model:GameViewModel by lazy{
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.
        getInstance(requireActivity().application))[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.firstBtn)
            add(binding.secondBtn)
            add(binding.thirdBtn)
            add(binding.fourthBtn)
            add(binding.fifthBtn)
            add(binding.sixthBtn)
        }
    }

        private var _binding: FragmentGameBinding? = null
        private val binding: FragmentGameBinding
        get() = _binding?:throw RuntimeException("Binding is null")

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            parseArgs()
        }
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentGameBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            observeViewModel()
            setClickListenersOnOptions()
            model.startGame(level)
        }

    private fun setClickListenersOnOptions(){
        for(option in tvOptions){
            option.setOnClickListener{
                model.chooseAnswer(option.text.toString().toInt())
            }
        }
    }

        private fun observeViewModel(){
            model.question.observe(viewLifecycleOwner){
                binding.sumBtn.text = it.sum.toString()
                binding.firstNumBtn.text = it.visibleNumber.toString()
                for(i in 0 until tvOptions.size){
                    tvOptions[i].text = it.options[i].toString()
                }
            }
            model.percentOfRightAnswers.observe(viewLifecycleOwner){
                binding.progressBar.setProgress(it, true)
            }
            model.enoughCountOfRightAnswers.observe(viewLifecycleOwner){
                binding.infoTxt.setTextColor(getColorByState(it))
            }
            model.enoughPercentOfRightAnswers.observe(viewLifecycleOwner){
                val color= getColorByState(it)
                binding.progressBar.progressTintList = ColorStateList.valueOf(color)
            }

            model.formattedTime.observe(viewLifecycleOwner){
                binding.timer.text = it
            }

            model.minPercent.observe(viewLifecycleOwner){
                binding.progressBar.secondaryProgress = it
            }

            model.gameResult.observe(viewLifecycleOwner){
                startGameFinishedFragment(it)
            }
            model.progressAnswers.observe(viewLifecycleOwner){
                binding.infoTxt.text = it
            }
        }
    private fun getColorByState(goodState: Boolean):Int{
        val colorResId = if(goodState)android.R.color.holo_green_light
        else android.R.color.holo_red_light
        return ContextCompat.getColor(requireContext(),colorResId)
    }
        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }

        private fun parseArgs(){
            requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
                level = it
            }
        }

        private fun startGameFinishedFragment(gameResult: GameResult){
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container,GameFinishedFragment.newInstance(gameResult))
                .addToBackStack(null)
                .commit()
        }

        companion object{
        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level):GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }

}