package com.aldreduser.my2_waydatabinding.mutablelivedata

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.aldreduser.my2_waydatabinding.R

// https://www.youtube.com/watch?v=suC0OM5gGAA
// observer is observing the timer and when the data is changed the text view is updated
// normally these things would be used with a viewModel but I'm keeping it simple
//      -the .observe() would be in the view
// The timer goes from 10 to 0, when kt stops, a toast is displayed

class MutableLivedataFragment : Fragment() {

    private lateinit var timer: CountDownTimer
    private val _seconds = MutableLiveData<Int>()   //private
    val seconds: LiveData<Int> get() = _seconds     //public   //this is to be used from outside this class
    var finished = MutableLiveData<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mutable_livedata, container, false)

        startTimer()
        val timerTxt = view.findViewById<TextView>(R.id.timer_txt)

        _seconds.observe(viewLifecycleOwner, Observer {
            // 'it' refers to 'seconds()'
            timerTxt!!.text = it.toString()
        })
        finished.observe(viewLifecycleOwner, Observer {
            // 'it' refers to 'finished'
            if(it) {
                Toast.makeText(context, "Finished!", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }

    fun startTimer() {
        timer = object : CountDownTimer(10000, 1000) {
            override fun onFinish() {
                finished.value = true
            }

            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished/1000
                _seconds.value = timeLeft.toInt()
            }
        }.start()
    }

    fun stopTimer() {
        timer.cancel()
    }
}