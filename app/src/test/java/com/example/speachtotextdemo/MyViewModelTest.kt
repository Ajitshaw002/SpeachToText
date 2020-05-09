package com.example.speachtotextdemo

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.speachtotextdemo.network.MyApi
import com.example.speachtotextdemo.network.NetworkConnectionInterCeption
import com.example.speachtotextdemo.reposotory.Reposotory
import com.example.speachtotextdemo.viewModel.ActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MyViewModelTest {

    @Mock
    lateinit var context: Context
    lateinit var SystemUnderTestViewModel: ActivityViewModel
    lateinit var networkConnectionInterCeption: NetworkConnectionInterCeption
    lateinit var myApi: MyApi
    lateinit var reposotory: Reposotory

    @Before
    fun setup() {

        networkConnectionInterCeption = NetworkConnectionInterCeption(context)
        myApi = MyApi.invoke(networkConnectionInterCeption)
        reposotory = Reposotory(myApi)
        SystemUnderTestViewModel = ActivityViewModel(context, reposotory)

    }

    @Test
    fun view_return_null_value(): Unit {

        val list = SystemUnderTestViewModel.getdata()
        assertThat(list, `is`(IsNull.nullValue()))
    }

    }



