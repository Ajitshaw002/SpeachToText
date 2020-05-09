package com.example.speachtotextdemo

import android.app.Application
import com.example.speachtotextdemo.network.MyApi
import com.example.speachtotextdemo.network.NetworkConnectionInterCeption
import com.example.speachtotextdemo.reposotory.Reposotory
import com.example.speachtotextdemo.viewModel.MyViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MyApplication:Application(),KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        /** create singleton instance of Network connection**/
        bind() from singleton{
            NetworkConnectionInterCeption(instance())
        }

        /** create singleton instance of MyAPI**/
        bind() from singleton{
            MyApi(instance())
        }

        /** create singleton instance of Reposotory**/
        bind() from singleton{
            Reposotory(instance())
        }

        /** create instance of ViewModelFactory**/
        bind() from singleton{
            MyViewModelFactory(instance(),instance())
        }
    }

}