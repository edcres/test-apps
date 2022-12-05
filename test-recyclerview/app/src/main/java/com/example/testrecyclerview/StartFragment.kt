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
    private lateinit var oneWidgetBtn: Button
    private lateinit var widgetsInterfaceBtn: Button
    private lateinit var recyclerDotAdapterBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        lAAdapterBtn = view.findViewById(R.id.l_a_adapter_btn)
        oneWidgetBtn = view.findViewById(R.id.one_widget_btn)
        widgetsInterfaceBtn = view.findViewById(R.id.widgets_interface_btn)
        recyclerDotAdapterBtn = view.findViewById(R.id.recycler_dot_adapter_btn)

        lAAdapterBtn.setOnClickListener {
            val navController = Navigation.findNavController(requireParentFragment().requireView())
            navController.navigate(R.id.action_startFragment_to_LAFragment)
        }
        oneWidgetBtn.setOnClickListener {
            val navController = Navigation.findNavController(requireParentFragment().requireView())
            navController.navigate(R.id.action_startFragment_to_oneWidgetInterfaceFragment)
        }
        widgetsInterfaceBtn.setOnClickListener {
            val navController = Navigation.findNavController(requireParentFragment().requireView())
            navController.navigate(R.id.action_startFragment_to_clickInterfaceFragment)
        }
        recyclerDotAdapterBtn.setOnClickListener {
            val navController = Navigation.findNavController(requireParentFragment().requireView())
            navController.navigate(R.id.action_startFragment_to_recyDotAdapterFragment)
        }

        return view
    }
}