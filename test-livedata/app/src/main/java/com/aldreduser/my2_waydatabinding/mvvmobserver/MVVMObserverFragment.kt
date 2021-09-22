package com.aldreduser.my2_waydatabinding.mvvmobserver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aldreduser.my2_waydatabinding.R

class MVVMObserverFragment : Fragment() {

    private lateinit var viewModel: TVViewModel
    private lateinit var tvBtn: Button
    private lateinit var tvBooleanTxt: TextView
    private lateinit var tvTxt: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mvvm_observer, container, false)
        viewModel = ViewModelProvider(this).get(TVViewModel::class.java)
        setUIWidgets(view)
        eventListeners()

        viewModel.currentNumber.observe(viewLifecycleOwner, Observer { num ->
            tvTxt.text = num.toString()
        })
        viewModel.currentBoolean.observe(viewLifecycleOwner, Observer { bool ->
            tvBooleanTxt.text = bool.toString()
        })

        return view
    }

    private fun eventListeners() {
        tvBtn.setOnClickListener {
            viewModel.incrementText()
        }
    }

    private fun setUIWidgets(view: View) {
        tvBtn = view.findViewById(R.id.tv_btn)
        tvBooleanTxt = view.findViewById(R.id.tv_boolean_txt)
        tvTxt = view.findViewById(R.id.tv_txt)
    }
}
