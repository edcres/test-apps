package com.example.testcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

// https://www.youtube.com/watch?v=rHKeRWK3zL4

@Composable
fun TestScreen() {

    Column() {

        Row(
            modifier = Modifier
                .width(300.dp)
                .fillMaxHeight(0.25f)     // fills 25% of the screen
                .background(Color.Cyan),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Hello")
            Text(text = "World")
        }

        Column(
            modifier = Modifier
                .background(Color.Green)
                .fillMaxHeight(0.3f)        // 30% of what's left
                .fillMaxWidth()
                .border(5.dp, Color.Red)
                .padding(16.dp)
                .border(5.dp, Color.Magenta)       // Shape is 'RectangleShape' by default. Could be CircleShape
                .padding(8.dp,)
                .border(5.dp, Color.Blue)
        ) {
            Text(text = "Hello 2", modifier = Modifier
                .offset(25.dp, 10.dp) /*'.offset()' replaces margin. Works with padding.*/
                .border(5.dp, Color.Blue)
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(text = "World", modifier = Modifier.clickable {
                // does nothing for now
            }/*.scrollable().draggable()*/)
        }

    }

    // modifiers can be used to make the widgets interactable: ie. draggable

//    Column(
//        verticalArrangement = Arrangement.Center,
//    ) {
//        Text(
//            text = "Test Screen"
//        )
//    }
}