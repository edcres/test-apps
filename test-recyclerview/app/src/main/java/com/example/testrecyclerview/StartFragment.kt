package com.example.testrecyclerview


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

private const val TAG = "StartFrag_TAG"

class StartFragment : Fragment() {

    private lateinit var lAAdapterBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        lAAdapterBtn = view.findViewById(R.id.l_a_adapter_btn)

        lAAdapterBtn.setOnClickListener {
            val navController = Navigation.findNavController(requireParentFragment().requireView())
            navController.navigate(R.id.action_startFragment_to_LAFragment)
        }

        return view
    }
}