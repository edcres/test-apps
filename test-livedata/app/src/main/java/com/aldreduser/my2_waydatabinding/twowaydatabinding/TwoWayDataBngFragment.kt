package com.aldreduser.my2_waydatabinding.twowaydatabinding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.aldreduser.my2_waydatabinding.databinding.ActivityMainBinding.inflate
import com.aldreduser.my2_waydatabinding.databinding.FragmentTwoWayBindingBinding

class TwoWayDataBngFragment : Fragment() {

    private var binding: FragmentTwoWayBindingBinding? = null
    private val twoWayDataBngViewModel: TwoWayDataBngViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentTwoWayBindingBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = twoWayDataBngViewModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}