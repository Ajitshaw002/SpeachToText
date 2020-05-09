package com.example.speachtotextdemo.reposotory


import com.example.speachtotextdemo.network.MyApi
import com.example.speachtotextdemo.responce.Dictionary
import java.util.*
import kotlin.collections.ArrayList

class Reposotory(val myApi: MyApi) {

    lateinit var dataList: List<Dictionary>

    //hit the api using retrofit & sort the result
    suspend fun getDatafromServer(): List<Dictionary> {
        dataList = ArrayList()
        val mainResponces = myApi.getDataFromServer().dictionary
        Collections.sort(mainResponces)
        dataList = mainResponces
        return dataList
    }
}