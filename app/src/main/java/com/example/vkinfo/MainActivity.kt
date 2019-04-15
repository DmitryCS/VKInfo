package com.example.vkinfo

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

import com.example.vkinfo.utils.NetworkUtils
import com.example.vkinfo.utils.NetworkUtils.Companion.generateURL
import com.example.vkinfo.utils.NetworkUtils.Companion.getResponseFromURL
import org.json.JSONArray
import org.json.JSONObject
import java.net.CacheResponse

class MainActivity : AppCompatActivity() {
    private var searchField: EditText? = null
    private var searchButton: Button? = null
    private var result: TextView? = null
    private var errorMessage: TextView? = null
    private var loadingIndicator: ProgressBar? = null

    class VKQueryTask(result:TextView, errorMessage: TextView, loadingIndicator: ProgressBar): AsyncTask<URL, Void, String>() {
        var textViewResult: TextView = result
        var textViewError: TextView = errorMessage
        var progressBarLoadIndicator: ProgressBar = loadingIndicator

        private fun showResultTextView() {
            textViewResult?.visibility = View.VISIBLE
            textViewError?.visibility = View.INVISIBLE
        }

        private fun showErrorTextView(){
            textViewResult?.visibility = View.INVISIBLE
            textViewError?.visibility = View.VISIBLE
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressBarLoadIndicator?.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: URL?): String {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            var response: String? = getResponseFromURL(params[0]!!)

            return response.toString()
            //result?.setText(response)
        }

        override fun onPostExecute(response: String?) {
            var firstName: String? = null
            var lastName: String? = null

            if(response != null && !response.equals("")) {
                super.onPostExecute(response)
                var jsonResponse: JSONObject = JSONObject(response)
                var jsonArray: JSONArray = jsonResponse.getJSONArray("response")
                var userInfo: JSONObject = jsonArray.getJSONObject(0)
                firstName = userInfo.getString("first_name")
                lastName = userInfo.getString("last_name")
                var resultingString: String = "Имя: " + firstName + "\n" + "Фамилия: " + lastName
                textViewResult.setText(resultingString)

                showResultTextView()
            } else{
                showErrorTextView()
            }
//            setTText(res)
            progressBarLoadIndicator?.visibility = View.INVISIBLE

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchField = findViewById(R.id.et_search_field)
        searchButton = findViewById(R.id.b_search_vk)
        result = findViewById(R.id.tv_result)
        errorMessage = findViewById(R.id.tv_error_message)
        loadingIndicator = findViewById(R.id.pb_loading_indicator)

        searchButton?.setOnClickListener {
            var generatedURL: URL = generateURL(searchField?.getText().toString())
            //var response: String? = getResponseFromURL(generatedURL)
            //result?.setText(response)
//            result = VKQueryTask(this).execute(generatedURL)
            VKQueryTask(result!!, errorMessage!!, loadingIndicator!!).execute(generatedURL)
            result?.setText(generatedURL.toString())
            //result?.setText("Кнопка была нажата")
        }
    }
}


class MyThread: Thread(){
    override public fun run(){
        for (i:Int in 1..1000){
            System.out.print(i)
        }
    }
}