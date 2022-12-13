package com.aldreduser.testapp.callbacklambdaclosures

/** Callback
 * A call back is a function that is passed into another function.
 *      This other function is expected to callback/ execute the argument at some given time
 *
 */

/** Closures
 *
 * If a callback interface only has one function, a callback can typically be converted into a closure.
 *      (or more generally, a lambda)
 */

/** Lambda
 *
 * A function that can be stored as a variable
 */

// https://www.youtube.com/watch?v=icqv4isFopo
/**
 * the main purpose of callbacks and closures are asynchronous programming and event listening
 */

// Below link is an example of a lambda with a return type (Boolean)
// https://www.youtube.com/watch?v=wnyN8umZIRM

fun main() {
//    rollDice(1..6, 4, {
//        println(it)
//    })
    // Below code is another way of writing the above code.
    // can move lambda out of parenthesis bc it is the last argument it takes, it just looks better
    rollDice(1..6, 4) {
        println(it)
    }
}

// taking a function as an argument (lambda)
fun rollDice(
    range: IntRange,
    time: Int,      // how many times to roll the dice
    callback: (result: Int) -> Unit        // returning 'Unit' means the same as returning nothing
) {
    for(i in 0 until time) {
        val result = range.random()
        callback(result)
    }
}
