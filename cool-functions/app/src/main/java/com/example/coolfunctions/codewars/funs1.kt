package com.example.coolfunctions.codewars

import kotlin.math.pow

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
        ass.split(" ").flatMap { it.split(" ") }
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
}

fun zipInts() {
    val arr = Array<String>(3)
    arr.toMutableList().removeIf()


    when()
}




object DirReduction {
    fun dirReduc(arr: Array<String>): Array<String> {
        println(arr.toMutableList())
        val directionsMap = mapOf("N" to "NORTH","E" to "EAST","S" to "SOUTH","W" to "WEST")
        var newDirs = arr.toMutableList()
        var lookMore = true
        while (lookMore) {
            var lastDir = ""
            lookMore = false
            for (i in newDirs.size - 1 downTo 0) {
                if (lastDir == getOpposite( newDirs[i], directionsMap )) {
                    newDirs.removeAt(i + 1)
                    lookMore = true
                }
                lastDir = newDirs[i]
            }
        }
        println(newDirs)
        return newDirs.toTypedArray()
    }
}

fun getOpposite(thisDir: String, directionsMap: Map<String, String>): String {
    return when (thisDir) {
        directionsMap["N"] -> directionsMap["S"]!!
        directionsMap["S"] -> directionsMap["N"]!!
        directionsMap["W"] -> directionsMap["E"]!!
        directionsMap["E"] -> directionsMap["W"]!!
        else -> ""
    }
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