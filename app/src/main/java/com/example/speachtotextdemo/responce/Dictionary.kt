package com.example.speachtotextdemo.responce

data class Dictionary(var word:String, var frequency:Int): Comparable<Dictionary> {

    //get the data as per frequency in descending order
    override fun compareTo(other: Dictionary): Int {

        return if (frequency > other.frequency) {
            -1
        } else if (frequency < other.frequency) {
            1
        } else {
            0
        }
    }
}