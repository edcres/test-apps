package com.example.testrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * adapters can use:
 * - dataBinding or not
 * - RecyclerView.Adapter or ListAdapter
 * - Handle click events:
 *      - Interface for click events (normal best practice way)
 *      - Lambdas (handled in the view like with an interface) (also best practice but newer and not to common)
 *      -In the adapter without an interface or lambda (not good, breaks separation of concerns)
 *
 * - Using dataBinding makes it a lot easier to use the specific item within the ViewHolder
 */

// I believe I've never used RecyclerView.Adapter with dataBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}