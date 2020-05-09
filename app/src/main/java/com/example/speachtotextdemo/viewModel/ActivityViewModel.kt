package com.example.speachtotextdemo.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.speachtotextdemo.R
import com.example.speachtotextdemo.reposotory.Reposotory
import com.example.speachtotextdemo.network.NoInternetException
import com.example.speachtotextdemo.responce.Dictionary
import com.example.speachtotextdemo.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ActivityViewModel(val context: Context, val reposotory: Reposotory) : ViewModel() {


    var listdata = MutableLiveData<List<Dictionary>>()
    lateinit var list: ArrayList<Dictionary>
    var flag:Boolean= false

    var positionUpdate = MutableLiveData<Int>()

    fun getdata():LiveData<List<Dictionary>>{
        //get the data from the Repository & set it into live data
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val list = reposotory.getDatafromServer()
                listdata.value = list
            }catch (e: NoInternetException){
                context.toast(e.message!!)
            }
        }

        return listdata
    }

    fun dataFromSpeach(speachText: String) {

            list = listdata.value as ArrayList

        val newList = ArrayList<Dictionary>()
        /**check whether the data list contain the speech text then set it to live data**/
        for(i in 0..list.size-1){
            try {
                if(list.get(i).word.toLowerCase().contains(speachText.toLowerCase())) {
                    //make the flag true for no data found check
                    flag = true
                    list.get(i).frequency++
                    newList.addAll(list)
                    Collections.sort(newList)
                    listdata.value = newList
                    break
                }else{
                    flag=false
                }
            }catch (e:Exception){
           context.toast(context.getString(R.string.no_data_found))
            }
        }
        //if data not found from speech
        if(!flag){
            context.toast(context.getString(R.string.no_data_found))
        }
        /** take the position from the latest list to notify Adapter**/
        for(j in 0..newList.size-1){
                if(newList.get(j).word.toLowerCase().equals(speachText.toLowerCase())){
                    positionUpdate.value = j
                }
        }

    }
}