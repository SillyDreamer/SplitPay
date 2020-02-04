package com.example.myapp.presenter

import android.content.Context
import com.example.myapp.contract.QrContract
import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.Repository
import org.json.JSONObject
import java.util.regex.Pattern



class QrPresenter(val context : Context) : QrContract.Presenter {

    var parse: ArrayList<Product> = arrayListOf()


    override fun onButtonWasClicked(qrResult: String?) {

        val runnable = Runnable {
            val model = Model(context)
            model.dropTable()
            model.addToDBCheck((model.showCheck() + 1).toString())
            val arr = parseQr(qrResult)
            val message = Repository().loadMessage(arr[0], arr[1])
            parseResult(message)
            for (product in parse) {
                model.addToDBProduct(product)
            }
        }
        Thread(runnable).start()
    }

    private fun parseResult(content: String) {
        val test = JSONObject(content).getJSONObject("document").getJSONObject("receipt").getJSONArray("items")

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