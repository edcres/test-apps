package com.example.testallnavigation.fragmenttofragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testallnavigation.FRAG_TO_FRAG_INTENT_KEY
import com.example.testallnavigation.R

/**
 * https://developer.android.com/guide/fragments/communicate
 * "you should not have fragments communicate directly with other fragments or with its host activity."
 *
 *  It looks like using a communicator interface is the best way to do this
 * https://www.youtube.com/watch?v=rpzuEN8UhUQ
 *
 * - I didn't set up the fragments because I already learned how
 *      this is done using 'supportFragmentManager'. Maybe I'll finish it one day.
 *
 *
 * This tutorial below looks like the fragments communicate directly but google recommends against this.
 * https://www.youtube.com/watch?v=ougoYqlPYeY
 *      - the start the transaction from the first fragment directly to the second using 'supportFragmentManager.beginTransaction'
 *
 */


// developer.android.com recommends either a sharedViewModel or 'setFragmentResultListener()'
//setFragmentResultListener("requestKey") { requestKey, bundle ->
//    // We use a String here, but any type that can be put in a Bundle is supported
//    val result = bundle.getString("bundleKey")
//    // Do something with the result
//}


class FragToFragActivity : AppCompatActivity(), ViewCommunicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frag_to_frag)
    }

    override fun passDataCom(input: String) {
        val bundle = Bundle()
        bundle.putString(FRAG_TO_FRAG_INTENT_KEY, input)

        val transaction = supportFragmentManager.beginTransaction()
//        val fragmentB = // instantiate receiver fragment
//        fragmentB.arguments = bundle
//        transaction.replace(R.id.fragment_container, fragmentB)
        transaction.commit()
    }
}