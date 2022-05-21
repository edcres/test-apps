package com.aldreduser.testapp.singleton

import android.util.Log

/**
 * Singleton means: only a single object
 * A single instance of a class
 *
 * Useful for an object that is expensive to create and is used very frequently
 *
 * A companion object in kotlin is static instance in other languages
 *
 *
 */

private const val TAG = "TextSingle__TAG"

fun main() {
    val managerSingleton1 = Manager.getInstance()
    val managerSingleton2 = Manager.getInstance()
    val managerSingleton3 = Manager.getInstance()

    val managerNormal1 = Manager()
    val managerNormal2 = Manager()
    val managerNormal3 = Manager()

    println("testSingleton:" +
            "\n$managerSingleton1\n$managerSingleton2\n$managerSingleton3" +
            "\n$managerNormal1\n$managerNormal2\n$managerNormal3")
}

//class Manager private constructor() {
class Manager() {
    // https://www.youtube.com/watch?v=Vuwa21Xy-dc
    companion object {
        // the instance is probably the singleton
        private var instance: Manager? = null
        fun getInstance() = synchronized(this) {
            if (instance == null) {
                instance = Manager()
            }
            instance
        }
    }
}