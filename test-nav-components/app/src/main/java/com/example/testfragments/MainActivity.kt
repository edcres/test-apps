package com.example.testfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

// prevent bug: make sure FragmentContainerView has:
//      'android:name="androidx.navigation.fragment.NavHostFragment"'

/** This app uses the Jetpack Navigation Component
 * - Navigation Graph
 *      -an xml resource file that defines all possible paths a user can take through an app
 *      -it shows all the destinations that can be reached
 * - Nav Host
 *      -an empty container that displays fragment destinations (fragments) from the Navigation Graph
 *      -NavHostFragment is the default implementation of this
 * -NavController
 *      -an object that MANAGES app navigation with the Nav Host
 *      -orchestrates the swapping of destination content in the Nav Host
 * source: https://www.youtube.com/watch?v=DI0NIk-7cz8
*/

//steps:
// create a navigation directory in res package
//  -go to resource manager tab -> navigation tab -> '+' -> create resource file, name it ~'my_navigation'
//  -this way dependencies are added automatically
// create fragments inside this .xml file (the Navigation graph) and assign a start destination
// add the NavHostFragment widget to the activity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
