package com.example.testui.materialbottomsheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testui.R

class NavCompActivity : AppCompatActivity(), BottomSheetFragment.OnBottomSheetCallListener {

    private var modalBottomSheet: ModalBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_comp)
    }

    override fun sendTestString(testString: String) {
//        if (modalBottomSheet == null) {
//            modalBottomSheet = ModalBottomSheet.newInstance(testString)
//            modalBottomSheet?.show(supportFragmentManager, modalBottomSheet?.tag)
//        } else {
//            modalBottomSheet?.updateContent(testString)
//        }
        modalBottomSheet = ModalBottomSheet.newInstance(testString)
//        modalBottomSheet?.setStyle()
        modalBottomSheet?.show(supportFragmentManager, modalBottomSheet?.tag)
    }
}