package com.example.speachtotextdemo.view

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speachtotextdemo.viewModel.ActivityViewModel
import com.example.speachtotextdemo.viewModel.MyViewModelFactory
import com.example.speachtotextdemo.R
import com.example.speachtotextdemo.adapter.RecyclerAdapter
import com.example.speachtotextdemo.responce.Dictionary
import com.example.speachtotextdemo.util.hideLoding
import com.example.speachtotextdemo.util.showLoding
import com.example.speachtotextdemo.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*


class MainActivity : AppCompatActivity(), KodeinAware {

    lateinit var viewModel: ActivityViewModel
    lateinit var layoutManager: LinearLayoutManager
    private val REQ_CODE_SPEECH_INPUT = 101
    var recyclerAdapter: RecyclerAdapter? = null


    override val kodein by kodein()

    /**take the instance from kodein dependency injection**/
    private val myViewModelFactory: MyViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //get the view model
        viewModel =
            ViewModelProviders.of(this, myViewModelFactory).get(ActivityViewModel::class.java)
        layoutManager = LinearLayoutManager(this)
        observeData()

        //set onclick on button
        btn_speak.setOnClickListener {
            startSpeechToText()
        }
    }

    private fun startSpeechToText() {
        //start the intent got getting speech
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.say_something)
        )
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            toast(getString(R.string.not_support_text))
        }


    }

    private fun observeData() {
        //observe the initial list
        loadingBar.showLoding()
        viewModel.getdata().observe(this, Observer { list ->
            setRecyClerView(list)
            loadingBar.hideLoding()
        })

        //observe the position of updated row
        viewModel.positionUpdate.observe(this, Observer {
            loadingBar.showLoding()
            /** delay half second for update the UI using CoRoutine**/
            CoroutineScope(Dispatchers.Main).launch {
                notifyToScroll(it)
                loadingBar.hideLoding()
            }
        })

    }

    suspend fun notifyToScroll(pos: Int) {
        //delay for half sec & then notify to Adapter for the change
        delay(500)
        recyclerView.scrollToPosition(pos)
        //notify adapter to scroll
        notifyAdapters(pos)

    }

    suspend fun notifyAdapters(pos: Int) {
        delay(500)
        recyclerView.adapter!!.notifyItemChanged(pos, "payload")
    }

    private fun setRecyClerView(list: List<Dictionary>?) {
        recyclerView.layoutManager = layoutManager
        recyclerAdapter = RecyclerAdapter(this, list!!)
        recyclerView.adapter = recyclerAdapter
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == RESULT_OK && null != data) {
                    val result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    /**send data to view model**/
                    viewModel.dataFromSpeach(result!![0])
                }
            }
        }
    }
}
