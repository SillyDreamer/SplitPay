package com.example.myapp.model

import com.example.myapp.MyApplication
import com.example.myapp.R
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern


//user - +79610577892
//password - 342154

class Repository {

    var parse: ArrayList<Product> = arrayListOf()
    var date = ""


    private fun loadMessage(arr: ArrayList<String>): String {
        lateinit var content: StringBuffer

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
                    println("\nResponse Code : $responseCode")
                        BufferedReader(InputStreamReader(inputStream)).use {
                            val response = StringBuffer()

                            var inputLine = it.readLine()
                            while (inputLine != null) {
                                response.append(inputLine)
                                inputLine = it.readLine()
                            }
                            it.close()
                            content = response
                            println("content = $content")
                        }
                    println("content1 = $content")
                }
            }
        }
        return content.toString()
    }


    private fun parseResult(content: String) {
        println("parse == $content")
        parse.clear()
        val test = JSONObject(content).getJSONObject("document").getJSONObject("receipt")
            .getJSONArray("items")
        date =
            JSONObject(content).getJSONObject("document").getJSONObject("receipt").get("dateTime")
                .toString()
        println("data = $date")
        test.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } }
            .map {
                parse.add(
                    Product(
                        it.get("name").toString(),
                        it.get("price").toString(),
                        it.get("quantity").toString()
                    )
                )
            }
    }

    fun parseQr(qr: String?): Pair<ArrayList<Product>, String> {
        val s: String =
            qr ?: "t=20191123T1821&s=1496.64&fn=9280440300065001&i=57638&fp=3805453234&n=1"
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
        val arr = arrayListOf(
            "https://proverkacheka.nalog.ru:9999/v1/inns/*/kkts/*/fss/$fnNum/tickets/$iNum?fiscalSign=$fpNum&sendToEmail=no",
            "https://proverkacheka.nalog.ru:9999/v1/ofds/*/inns/*/fss/$fnNum/operations/1/tickets/$iNum?fiscalSign=$fpNum&date=$timeNum2&sum=$sumNum"
        )
        var content = loadMessage(arr)
        while (content.isEmpty())
            content = loadMessage(arr)
        parseResult(content)
        return Pair(parse, date)
    }
}