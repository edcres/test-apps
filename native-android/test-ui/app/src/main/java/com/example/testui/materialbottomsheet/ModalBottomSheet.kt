package com.example.testui.materialbottomsheet

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.testui.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheet : BottomSheetDialogFragment() {

    private lateinit var strTxt: TextView
    private lateinit var strBtn: Button
    private lateinit var dialog: BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modal_bottom_sheet_content, container, false)
        strTxt = view.findViewById(R.id.str_txt)
        strBtn = view.findViewById(R.id.test_btn)

        strBtn.setOnClickListener {
            dialog.dismiss()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            strTxt.text = arguments?.getString(SHEET_STR_KEY)
            strBtn.text = arguments?.getString(SHEET_STR_KEY)
        }
    }

//    fun updateContent(testString: String) {
//        if (activity != null) {
//            Log.d(TAG, "onViewCreated: $testString")
//            strTxt.text = testString
//            strBtn.text = testString
//            dialog.show()
//        }
//    }
//
//    inner class MyHandler {
//        fun onClose(view: View) {
//            dialog.hide()
//        }
//    }

    companion object {
        const val TAG = "ModalBottomSheet_TAG"
        fun newInstance(testString: String) = ModalBottomSheet().apply {
            arguments = Bundle().apply {
                putString(SHEET_STR_KEY, testString)
            }
        }
    }
}