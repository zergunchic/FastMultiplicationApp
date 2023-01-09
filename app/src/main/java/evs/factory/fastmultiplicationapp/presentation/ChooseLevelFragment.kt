package evs.factory.fastmultiplicationapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import evs.factory.fastmultiplicationapp.R
import evs.factory.fastmultiplicationapp.databinding.FragmentChooseLevelBinding
import evs.factory.fastmultiplicationapp.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding?:throw RuntimeException("Binding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            testBtn.setOnClickListener {
                startGameFragment(Level.TEST)
            }
            easyBtn.setOnClickListener {
                startGameFragment(Level.EASY)
            }
            medBtn.setOnClickListener {
                startGameFragment(Level.NORMAL)
            }
            hardBtn.setOnClickListener {
                startGameFragment(Level.HARD)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    private fun startGameFragment(lvl: Level){
//        requireActivity().supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.main_container,GameFragment.newInstance(lvl))
//            .addToBackStack(GameFragment.NAME)
//            .commit()
//    }
    private fun startGameFragment(lvl: Level){
        val args = Bundle().apply {
            putParcelable(GameFragment.KEY_LEVEL, lvl)
        }
        findNavController().navigate(R.id.action_chooseLevelFragment_to_gameFragment, args)
    }


    companion object {

        const val NAME="ChooseLVL"
        fun newInstance():ChooseLevelFragment{
            return ChooseLevelFragment()
        }
    }

}