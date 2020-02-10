package com.example.myapp.presenter


import com.example.myapp.contract.QrContract
import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.Repository
import com.example.myapp.utils.Runner
import com.example.myapp.view.QrActivity
import org.json.JSONObject
import java.util.regex.Pattern



class QrPresenter(val model : Model, val runner : Runner, val rep : Repository) : QrContract.Presenter {

    var parse: ArrayList<Product> = arrayListOf()
    var date = ""

    override fun onButtonWasClicked(qrResult: String?) {

        runner.runInBackground(Runnable {
            val arr = parseQr(qrResult)
            val message = rep.loadMessage(arr[0], arr[1])
            parseResult(message)
            model.addToDBCheck(date)
            for (product in parse) {
                model.addToDBProduct(product)
            }
        })
    }

    private fun parseResult(content: String) {
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
