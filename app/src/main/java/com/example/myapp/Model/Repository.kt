package com.example.myapp.Model

import android.os.AsyncTask
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL

//user - +79610577892
//password - 342154

class Repository {

    var task = GetAsyncTask()
    fun loadMessage(url1 : String, url2 : String): String {
        task.execute(url1, url2)

        Log.d("123", "onCreateloadmessage")
        return task.get()
    }
}


class GetAsyncTask : AsyncTask<String, String, String>() {
    override fun doInBackground(vararg params: String?): String {
        var content = ""

        with(URL(params[1]).openConnection() as HttpURLConnection) {

            requestMethod = "GET"
            setRequestProperty("Device-id", "1")

            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Device-os", "Android 5.1")

            println("\nResponse Code : $responseCode")

            if (responseCode == 204) {
                with(URL(params[0]).openConnection() as HttpURLConnection) {
                    requestMethod = "GET"
                    val basicAuth = "Basic Kzc5NjEwNTc3ODkyOjM0MjE1NA=="
                    setRequestProperty("Authorization", basicAuth)
                    setRequestProperty("Device-id", "1")
                    setRequestProperty("Content-Type", "application/json; utf-8")
                    setRequestProperty("Accept", "application/json")
                    setRequestProperty("Device-os", "Android 5.1")

                    inputStream.bufferedReader().use {
                        it.lines().forEach { line ->
                            content += line + "\n"
                            println(line+"\n")
                        }
                    }
                }
            }
            else if (responseCode == 406) {
                println("code == 406")
            }
        }
        return content
    }

}