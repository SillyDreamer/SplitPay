package com.example.myapp.model

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import java.util.regex.Pattern


//user - +79610577892
//password - 342154

class Repository {

    var parse: ArrayList<Product> = arrayListOf()
    var date = ""

    private val mExecutor = Executors.newSingleThreadExecutor()
    private val mHandler = Handler(Looper.getMainLooper())

    //var task = GetAsyncTask()
    fun loadMessage(qrResult : String?) : Pair<ArrayList<Product>, String> {

        val arr = parseQr(qrResult)

       // mExecutor.submit(Runnable {
            var content = ""

            with(URL(arr[1]).openConnection() as HttpURLConnection) {

                requestMethod = "GET"
                setRequestProperty("Device-id", "1")

                setRequestProperty("Content-Type", "application/json; utf-8")
                setRequestProperty("Accept", "application/json")
                setRequestProperty("Device-os", "Android 5.1")

                println("\nResponse Code : $responseCode")

                if (responseCode == 204) {
                    with(URL(arr[0]).openConnection() as HttpURLConnection) {
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
                            }
                        }
                        println("content1 = $content")
                    }
                }
                else if (responseCode == 406) {
                    println("code == 406")
                }
            }
            //mHandler.post(Runnable {
                println("content2 = $content")
                parseResult(content)
            //})
        //})









//        task.execute(arr[0], arr[1])
//
//        Log.d("123", "onCreateloadmessage")
//        var test = task.get()
//
//        println("test = $test")
//
//        parseResult(test)
        return Pair(parse, date)
    }


    private fun parseResult(content: String) {
        parse.clear()
        val test = JSONObject(content).getJSONObject("document").getJSONObject("receipt").getJSONArray("items")
        date = JSONObject(content).getJSONObject("document").getJSONObject("receipt").get("dateTime").toString()

        test.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } }
            .map { parse.add(Product(it.get("name").toString(), it.get("price").toString(), it.get("quantity").toString()))}
    }

    private fun parseQr(str: String?): ArrayList<String> {
        val s: String = str ?: "t=20191123T1821&s=1496.64&fn=9280440300065001&i=57638&fp=3805453234&n=1"
        val m = Pattern.compile("(?<==)[^&]+").matcher(s)
        m.find()
        val timeNum = s.substring(m.start(), m.end())
        val timeNum2 = timeNum.substring(0, 4) + "-" +
                timeNum.substring(4, 6) + "-" +
                timeNum.substring(6, 11) + ":" +
                timeNum.substring(11, 13) + ":00"
        m.find()
        val sumNum = s.substring(m.start(), m.end()).replace(".", "")
        m.find()
        val fnNum = s.substring(m.start(), m.end())
        m.find()
        val iNum = s.substring(m.start(), m.end())
        m.find()
        val fpNum = s.substring(m.start(), m.end())
        return arrayListOf(
            "https://proverkacheka.nalog.ru:9999/v1/inns/*/kkts/*/fss/$fnNum/tickets/$iNum?fiscalSign=$fpNum&sendToEmail=no",
            "https://proverkacheka.nalog.ru:9999/v1/ofds/*/inns/*/fss/$fnNum/operations/1/tickets/$iNum?fiscalSign=$fpNum&date=$timeNum2&sum=$sumNum"
        )
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