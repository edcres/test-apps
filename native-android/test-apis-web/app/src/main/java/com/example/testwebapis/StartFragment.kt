package com.example.testwebapis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.testwebapis.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(requireParentFragment().requireView())
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            rndCatsBtn.setOnClickListener {
                navController.navigate(R.id.action_startFragment_to_randomCatsFragment)
            }
            observeConnectivityBtn.setOnClickListener {
                navController.navigate(R.id.action_startFragment_to_obsConnectivityFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}