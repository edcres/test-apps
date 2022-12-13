package com.example.testcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    var textToChange by remember {
        mutableStateOf("")
    }

    // Row or Column
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        TextField(
            value = textToChange,
            onValueChange = {
                textToChange = it
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            // if arg (name) is null, the app crashes
            onClick = {
                navController.navigate(Screen.DetailScreen.withArgs(textToChange))
            },
            modifier = Modifier.align(Alignment.End).padding(8.dp)//.offset(8.dp)
        ) {
            Text(text = "To Detail Screen (need an input in the text box)")
        }

        Button(
            onClick = {
                navController.navigate(Screen.TestScreen.route)
            },
            modifier = Modifier.align(Alignment.End).padding(4.dp)
        ) {
            Text(text = "To Test Screen")
        }

        Button(
            onClick = {
                navController.navigate(Screen.PrettyUIScreen.route)
            },
            modifier = Modifier.align(Alignment.End).padding(4.dp)
        ) {
            Text(text = "To Pretty UI Screen")
        }
    }
}