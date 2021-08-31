package com.example.testfirestorev2

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

var ship4Position = listOf<List<Int>>()
var ship2Position = listOf<List<Int>>()

fun main() {
    setUpGame()
    //userInput()
}

fun userInput() {
    //ship4Pos



    //ship2Pos

}

fun setUpGame() {
    //val mapXAxis = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
    //val mapYAxis = listOf<Int>(1,2,3,4,5,6,7,8,9,10)


    makeShip1()
    makeShip2()

    // check if it oiverlaps. if true remake ship 2
    var ship2overlaps: Boolean
    //if(ship2overlaps == true) {

    //}
}

fun makeShip1() {
    // ship 4 pos //
    val ship4PosOrientation = (0..1).random()
    val ship4PosOrientationString: String
    var ship4HorizontalPos: Int
    var ship4VerticalPos: Int
    if (ship4PosOrientation == 0) {
        // Horizontal
        ship4PosOrientationString = "Horizontal"
        ship4HorizontalPos = (1..7).random()	// don't go off bounds
        ship4VerticalPos = (1..10).random()
        ship4Position = listOf(
            listOf(ship4HorizontalPos, ship4HorizontalPos+1, ship4HorizontalPos+2, ship4HorizontalPos+3),
            listOf(ship4VerticalPos, ship4VerticalPos, ship4VerticalPos, ship4VerticalPos)
        )
    } else {
        // Vertical
        ship4PosOrientationString = "Vertical"
        ship4HorizontalPos = (1..10).random()	// don't go off bounds
        ship4VerticalPos = (1..7).random()
        ship4Position = listOf(
            //first x, then y
            listOf(ship4HorizontalPos, ship4HorizontalPos, ship4HorizontalPos,ship4HorizontalPos),
            listOf(ship4VerticalPos, ship4VerticalPos+1, ship4VerticalPos+2, ship4VerticalPos+3)
        )
    }
    println("4 cell ship: $ship4PosOrientationString: $ship4Position")
    // ship 4 pos //
}

fun makeShip2() {
    // ship 2 pos //
    ship2Position = listOf()


    val ship2PosOrientation = (0..1).random()
    val ship2PosOrientationString: String
    var ship2HorizontalPos: Int
    var ship2VerticalPos: Int
    if (ship2PosOrientation == 0) {
        // Horizontal
        ship2PosOrientationString = "Horizontal"
        ship2HorizontalPos = (1..7).random()	// don't go off bounds
        ship2VerticalPos = (1..10).random()
        ship2Position = listOf(
            listOf(ship2HorizontalPos, ship2HorizontalPos+1),
            listOf(ship2VerticalPos, ship2VerticalPos)
        )
    } else {
        // Vertical
        ship2PosOrientationString = "Vertical"
        ship2HorizontalPos = (1..10).random()	// don't go off bounds
        ship2VerticalPos = (1..9).random()
        ship2Position = listOf(
            //first x, then y
            listOf(ship2HorizontalPos, ship2HorizontalPos),
            listOf(ship2VerticalPos, ship2VerticalPos+1)
        )
    }
    println("2 cell ship: $ship2PosOrientationString: $ship2Position")
    // ship 2 pos //
}
