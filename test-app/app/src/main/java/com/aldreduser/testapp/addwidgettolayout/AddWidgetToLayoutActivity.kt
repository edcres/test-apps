package com.aldreduser.testapp.addwidgettolayout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.aldreduser.testapp.R
import kotlinx.android.synthetic.main.layout_widget_item_to_add.view.*

class AddWidgetToLayoutActivity : AppCompatActivity() {

    private lateinit var addedWidgetContainers: LinearLayout
    private val txtList = listOf("One", "Two", "Three", "Four")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_widget_to_layout)

        bindUIWidgets()

        for(i in 1..4) {
            addWidgetToLayout(i)
        }
    }

    private fun addWidgetToLayout(iterator: Int) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val linearLayoutItem: ConstraintLayout = findViewById(R.id.linear_item_container)

        val linearLayoutItem = inflater.inflate(R.layout.layout_widget_item_to_add, null)
//        var itemNumber: TextView = findViewById(R.id.item_number)
        val itemNumber = linearLayoutItem.item_number
        val itemTxt = linearLayoutItem.item_txt
        itemNumber.text = "$iterator"
        itemTxt.text = txtList[iterator-1]

        addedWidgetContainers.addView(linearLayoutItem)
    }

    private fun bindUIWidgets() {
        addedWidgetContainers = findViewById(R.id.added_widget_containers)
    }
}