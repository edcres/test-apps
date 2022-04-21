package com.example.testui.materialbottomsheet

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.testui.R

// https://princessdharmy.medium.com/implementing-modal-bottom-sheet-in-fragment-5fe751b6dec4

class BottomSheetFragment : Fragment() {

    private lateinit var sheetBtn: Button
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        sheetBtn = view.findViewById(R.id.sheet_btn)

        sheetBtn.setOnClickListener {
            if (listener != null) {
                listener!!.sendTestString("test string")
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener)
            listener = context
        else
            throw IllegalArgumentException("$context must implement OnFragmentInteractionListener")
    }

    interface OnFragmentInteractionListener {
        fun sendTestString(testString: String)
    }
}