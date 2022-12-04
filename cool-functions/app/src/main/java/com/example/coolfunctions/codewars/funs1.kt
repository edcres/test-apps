package com.example.coolfunctions.codewars

import kotlin.math.pow

// https://www.techiedelight.com/remove-null-values-from-list-kotlin/

private class funs1 {

    private fun sliceFun() {
        val arrayToSlice = listOf<Int>(9,8,7,6,5,4,3,2,1)
        // 'slice' returns a list of items in the specified indices
        arrayToSlice.slice(0..4)
        arrayToSlice.slice(listOf<Int>(0,2,4))

        // Returns a list containing first n elements.
        arrayToSlice.take(3)
        // Returns a list containing first n elements.
        arrayToSlice.takeLast(3)
        // Returns a list containing all elements except first n elements. (ie. drops the first n elements from the list)
        arrayToSlice.drop(3)
    }

    private fun transformFun() {
        val wordCode = listOf<Int>(9,8,7,6,5,4,3,2,1)
        wordCode.map {/*   { x -> MorseCode[x] ?: " " }   */}
        // Returns a list containing the results of applying the given transform function to each element in the original collection.
    }

    fun transformFlatFun() {
        val ass = "as df  h ghj"
        ass.length
        ass.split(" ").flatMap { it.split(" ") }
        ass.split(" ").toMutableList().removeAll(listOf("-"))
        // Returns a single list of all elements yielded from results of transform function
        // being invoked on each element of original collection.
    }

    fun transformIndexedFun() {
        // 'mapIndexed' Returns a list containing the results of applying the given transform function to
        // each element and its index in the original collection.
        val ass = "as dfh ghj"
        val p = 2
        ass.split(" ")
            .mapIndexed { i, c -> c.toString().toDouble().pow(p + i).toInt() }.sum()

        // 'reversed' Returns a string with characters in reversed order.
        ass.split(" ").joinToString(" ") { if (it.length > 4) it.reversed() else it }
    }

    fun foldIndexesFun() {
        // 'foldIndexed' Accumulates value starting with initial value and applying operation from left to
        // right to current accumulator value and each element with its index in the original collection.
        val ass = "as dfh ghj"
        val p = 3
        val n = 5
        ass.split("").foldIndexed(0) { i, n, str ->
            n.plus(1)
        }.let { if (it % n == 0) it / n else -1 }
    }

    fun trimStr() {
        val ass = "asdfghj "
        ass.trim()
        // Returns a string having leading and trailing whitespace removed.
    }

    fun filterFun() {
        // 'filter' Returns a list containing only elements matching the given predicate.
        val ass = "as dfh ghj"
        ass.split("").filter(String::isNotBlank)
    }

    fun splitIntList() {
        // 'singleOrNull' Returns the single element matching the given predicate, or null if element was
        // not found or more than one element was found.
        val integers = listOf<Int>(3,5,7,42,24,6,3,)
        integers.singleOrNull{ it % 2 == 0 }
        // 'partition' Splits the original collection into pair of lists, where first list contains elements for
        // which predicate yielded true, while second list contains elements for which predicate yielded false.
        integers.partition{ it % 2 == 0 }
        // 'filter' Returns a list containing only elements matching the given predicate.
        integers.filter { it % 2 == 0 }
        integers.filter { it % 3 == 0 || it % 5 == 0 }
    }

    fun zipInts() {
        val a = listOf<Int>(3,5,7,42,24,6,3,)
        val b = listOf<Int>(3,5,7,42,24,6,3,)
        // 'zip' Returns a sequence of values built from the elements of this sequence and the other
        // sequence with the same index. The resulting sequence ends as soon as the shortest input sequence ends.
        a.sortedDescending().asSequence()
            .zip(b.sortedDescending().asSequence())
    }

    // Returns a string containing last characters that satisfy the given predicate.
    fun takeLastWhileFun(str: String) = str.takeLastWhile { it.isDigit() }.let {
        if (it.isEmpty()) str + "1"
        else str.replace(it, "%0${it.length}d".format(it.toInt() + 1))



    }
}

//fun zipInts() {
//    val arr = Array<String>(3)
//    arr.toMutableList().removeIf()
//
//
//    when()
//}






// todo: incrementString
//import java.lang.Double.parseDouble

fun incrementString(str: String) : String {
    // Not taking decimals into account
    var newStr = str
    println("-----start----" + newStr)
    var numberToAdd = ""


    if (str.isEmpty() || !isInt(str.last().toString())) {
        newStr += "1"
    } else {

        // Take out the number from the string before inserting a new number
        for (i in newStr.length - 1 downTo 0) {
            if (isInt(  newStr[i].toString()   )) {
                numberToAdd += newStr[i]
            } else {
                break
            }
        }
        numberToAdd = numberToAdd.reversed()
        println(numberToAdd)


        // if the number starts with 0      if (numberToAdd[0] == "9")
        if (numberToAdd[0].toString() != "0") {
            newStr = newStr.dropLast( numberToAdd.length )
            numberToAdd = (numberToAdd.toInt() + 1).toString()
            newStr += numberToAdd
        } else {
            // count the number of 0s in the begining
//             var startingZeros = 0
//             for (i in 0 until numberToAdd.length - 1) {
//                 if (i == '0') {
//                     startingZeros ++
//                 }
//             }

            println(newStr)
            newStr = newStr.dropLast( (numberToAdd.toInt().toString().length) + 1 )
            println(newStr)

            // if carryOne
            var numberToAddPrev = numberToAdd.toInt().toString()



            var numberToAddPost = (numberToAdd.toInt() + 1).toString()

            println("prev = " + numberToAddPrev)
            println("post = " + numberToAddPost)


            if (numberToAddPrev.length < numberToAddPost.length) {
//                 println("post = " + numberToAddPost)
                numberToAdd = numberToAddPost
            } else {
                numberToAdd = "0" + numberToAddPost
            }

//             println(numberToAdd)

//             if (carries 1 to the 9) {
//                 newStr += numberToAdd.toInt()
//             } else {
//                 newStr += numberToAdd
//             }
            newStr += numberToAdd






//             val addToString = ""
//             var carryOne = false
//             var addZero = false

//             for (i in numberToAdd.length-1 downTo 0) {
//                 if (numberToAdd[0].toString() == "9") {
//                     addZero = true
//                 }

//                 if (numberToAdd[i].toString() != "9") {
//                         numberToAdd[i] = (numberToAdd[i].toInt() + 1).toString()
//                         carryOne = false
//                         break
//                     } else {
//                         numberToAdd[i] = 0
//                         carryOne = true
//                 }
//             }
//             if (addZero) {
//                 numberToAdd = "1" + numberToAdd
//             }






//             numberToAdd.forEach {
//                 if (newStr.last() != "9") {
//                     newStr.last()
//                 } else {

//                 }
//             }

        }

//        if (numberToAdd.toInt() == 0) {
//             newStr = newStr.dropLast( 1 )
//             newStr += "1"
//         } else {
//             newStr = newStr.dropLast( numberToAdd.length )
//             numberToAdd = (numberToAdd.toInt() + 1).toString()
//             newStr += numberToAdd
//         }
    }
    println(newStr)
    return newStr
}

fun isInt(num: String): Boolean {
    return try {
        num.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}











// todo: reorderWeights
fun orderWeight(string:String):String {
    val numsMap =  mutableMapOf<String, String>()
    var finalString = ""
    var previousConverted = ""
    val newWeights = mutableListOf<Int>()
    val repeatedWeights = mutableListOf<String>()  // original
    val trimmedNums = string.split(" ").map {
        val itTrimmed = it.trim()
        if (itTrimmed.isEmpty()) {
            "-"
        } else {
            numsMap.put(addDigits(it), it)
            newWeights.add(addDigits(it).toInt())

            if (previousConverted == addDigits(it)) {
                repeatedWeights.add(it)
            }
            previousConverted = addDigits(it)
            itTrimmed
        }
    }.filter { it != "-" }
    newWeights.sortedBy {
//        finalString += " " + numsMap[it.toString()]
//        numsMap[it.toString()].toInt()
        0
    }
    return finalString.trim()
    // Check if the weight of one number is the same as the adjusted weight of
    //      the number before, if so use alphabetical ordering.
}
fun addDigits(num: String): String {
    var addedDigits = 0
    num.forEach {
        addedDigits += num.toInt()
    }
    return addedDigits.toString()
}












// todo: convert RGB to HEX
fun rgb(r: Int, g: Int, b: Int): String {
    val rgb = listOf<String>(r.toString(), g.toString(), b.toString())
    return rgb.map { it ->
        val itInt = it.toInt()
        if (itInt < 0) {
            buildHex(0)
        } else if (itInt > 255) {
            buildHex(255)
        } else { buildHex(itInt) }
    }.joinToString()
}
fun buildHex(rgbNum: Int): String {
    var hexString = ""
    var hexNum = rgbNum
    while (hexNum > 0) {
        hexString = getHex(hexNum % 16) + hexString
        hexNum /= 16
    }
    return hexString
}
fun getHex(num: Int): Char {
    if (num >= 0 && num <= 9) {
        return (num + 48).toChar();
    }
    else {
        return (num - 10 + 65).toChar();
    }
}