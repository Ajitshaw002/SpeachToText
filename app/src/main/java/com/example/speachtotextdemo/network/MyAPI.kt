package com.example.speachtotextdemo.network


import com.example.speachtotextdemo.responce.MainResponces
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MyApi {

    //get service
    @GET("interview/dictionary-v2.json")
    suspend  fun getDataFromServer(): MainResponces

    companion object{
        //create a client of retrofit
        operator fun invoke(networkConnectionInterCeption: NetworkConnectionInterCeption): MyApi {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterCeption)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://a.galactio.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}