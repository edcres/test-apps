package com.aldreduser.testapp.addwidgettolayout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import com.aldreduser.testapp.R
import kotlinx.android.synthetic.main.layout_widget_item_to_add.view.*

class AddWidgetToLayoutActivity : AppCompatActivity() {

    private lateinit var addLayoutBtn: Button
    private lateinit var removeLayoutBtn: Button
    private lateinit var addedWidgetContainer: LinearLayout
    private val txtList = listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25)
    private var iterator = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_widget_to_layout)

        bindUIWidgets()

        addLayoutBtn.setOnClickListener {
            addWidgetToLayout()
        }
        removeLayoutBtn.setOnClickListener {
            removeWidget()
        }
    }

    private fun addWidgetToLayout() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val linearLayoutItem = inflater.inflate(R.layout.layout_widget_item_to_add, null)
        val itemNumber = linearLayoutItem.item_number
        val itemTxt = linearLayoutItem.item_txt
        itemNumber.text = "$iterator"
        iterator ++
        val textForView = "S${txtList[iterator-1]}S"
        itemTxt.text = textForView

        addedWidgetContainer.addView(linearLayoutItem)
    }

    private fun removeWidget() {
        addedWidgetContainer.removeViewAt(1)
//        addedWidgetContainer.removeAllViews()
    }

    private fun bindUIWidgets() {
        addLayoutBtn = findViewById(R.id.add_layout_btn)
        removeLayoutBtn = findViewById(R.id.remove_layout_btn)
        addedWidgetContainer = findViewById(R.id.added_widget_container)
    }
}