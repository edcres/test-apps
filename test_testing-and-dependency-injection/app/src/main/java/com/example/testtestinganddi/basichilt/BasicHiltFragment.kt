package com.example.testtestinganddi.basichilt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testtestinganddi.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// Coding with Mitch
// https://www.youtube.com/watch?v=pZJzdgG_45Y

/**
 * Field injection vs Constructor injection
 * - Constructor injection is better, field injection is simpler.
 * - The reason for dependency injection (Hilt/ Dagger2) is to make construction injection easier.
 */

/**
 * Hilt provides a standard way to incorporate Dagger dependency injection into an android application
 */

// If you want a class to be able to have dependencies injected into it, use @AndroidEntryPoint
@AndroidEntryPoint
class BasicHiltFragment : Fragment() {

    // Field injection (avoid this)
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_hilt, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        someClass.doAThing()
        someClass.doSomeOtherThing()
    }
}

// Mock class to declare some dependencies and inject them into 'BasicHiltFragment'
class SomeClass
@Inject //mark the constructor with @Inject yo add this class as a dependency for Dagger/Hilt
constructor(
    // constructor injection
    private val someOtherClass: SomeOtherClass
) {
    fun doAThing(): String {
        return "Look I did a thing"
    }
    fun doSomeOtherThing(): String {
        return someOtherClass.doSomeOtherThing()
    }
}

class SomeOtherClass
@Inject
constructor() {
    fun doSomeOtherThing(): String {
        return "Look I did another thing"
    }
}
