package com.example.coolfunctions.algorithms

fun main() {
    val algorithms = Algorithms()
    // algorithms.slidingDoor2("abcabcbb")
}

class Algorithms {

    fun slidingDoor2(s: String): Int {
        // sliding window
        // lengthOfLongestSubstring
        // https://leetcode.com/problems/longest-substring-without-repeating-characters/submissions/
        var startP = 0
        var leadP = 0
        var longest = 0
        val setList = mutableSetOf<Char>()

        while (leadP < s.length) {
            if (!setList.contains(s[leadP])) {
                setList.add(s[leadP])
                leadP++
                longest = maxOf(longest, setList.size)
            } else {
                // if the char is reapeated, remove the first char and update startP
                setList.remove(s[startP])
                startP++
            }
        }
        return longest
    }
}