package evs.factory.fastmultiplicationapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import evs.factory.fastmultiplicationapp.R
import evs.factory.fastmultiplicationapp.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding?:throw RuntimeException("Binding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.acceptBtn.setOnClickListener{
            launchChooseLevelFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//fragment transaction
//    private fun launchChooseLevelFragment(){
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, ChooseLevelFragment.newInstance())
//            .addToBackStack(ChooseLevelFragment.NAME)
//            .commit()
//    }
//navigation
    private fun launchChooseLevelFragment(){
        findNavController().navigate(R.id.action_welcomeFragment_to_chooseLevelFragment)
    }

    companion object{
        fun newInstance():WelcomeFragment {
            return WelcomeFragment()
        }
    }
}


