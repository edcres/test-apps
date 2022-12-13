package com.example.coolfunctions.datastructures

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

fun main() {
    // List
    val aList: ArrayList<Int> = ArrayList<Int>()

    // Map
    val hMap = HashMap<Int, String>()
    hMap.put(1, "Hussein")
    hMap.put(2, "Jenna")
    hMap.put(3, "Laya")
    hMap.get(3)
    hMap.forEach { (key, value) -> }
    val startTime = System.nanoTime()
    val endTime = System.nanoTime()
    val totalTimerTime = endTime - startTime
    val linkedHMap = LinkedHashMap<Int, String>()

    // Linked List
    val lList: LinkedList<Int> = LinkedList()
    lList.add(5)
    lList.addFirst(2)
    lList.remove()

    // Stack
    val stack = Stack<String>()
    // 'peek' Returns the object at the top of the Stack without removing it.
    stack.peek()
    // 'pop' Removes and returns the object at the top of the Stack.
    stack.pop()

    // Tree
    val treeSet = TreeSet<String>()
    val treeMap = TreeMap<String, String>()
}

class datastructures {
}