package com.example.testcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcompose.R
import kotlin.random.Random


// https://www.youtube.com/watch?v=KPVoQjwmWX4&list=PLtO7tIzYX2lXT4wyHVf271QzqNvFxIMFT&index=1&t=808s

@Composable
fun PrettyUIScreen() {

    val painter = painterResource(id = R.drawable.test_image_foreground)
    val description = "Kermit in the Snow"
    val title = "Kermit is playing in the snow"

    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .background(Color.Cyan)
            .padding(16.dp)
    ) {
        ImageCard(painter = painter, contentDescription = description, title = title)
        ColorBox(modifier = Modifier.fillMaxHeight(0.2f).fillMaxWidth())
    }
}

// state (dynamic content).
// Recomposing: redrawing UI components
@Composable
fun ColorBox(modifier: Modifier = Modifier) {
    val color = remember {
        // remembers the value through every recomposition
        // that way it wont be reset ot Yellow each time the Composable is called
        mutableStateOf(Color.Yellow)
    }


    Box(modifier = modifier
        .background(color.value)
        .clickable {
            color.value = Color(
                Random.nextFloat(),
                Random.nextFloat(),
                Random.nextFloat(),
                1f
            )
        })
}

// display an image in a card (with a lot of formatting)
@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        // to but composables inside of another
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop        //center crop in .xml
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 300f       //to do this correctly, calculate the dimensions, this only works for these dimensions
                    )
                )) {

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(title, style = TextStyle(color = Color.White), fontSize = 16.sp)
            }
        }
    }
}
