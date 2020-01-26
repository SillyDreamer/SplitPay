package com.example.myapp

import android.content.Context
import org.json.JSONObject
import java.util.regex.Pattern

class QrPresenter(val context : Context) : QrContract.Presenter {

    var parse: ArrayList<Product> = arrayListOf()



    override fun onButtonWasClicked(qrResult: String?) {
        var model: Model = Model(context)
        var arr = parseQr(qrResult)
        var message = Repository().loadMessage(arr[0], arr[1])
        parseResult(message)
        for (product in parse) {
            model.addToDBProduct(product)
        }
    }

    fun parseResult(content: String) {
        val t = JSONObject(content).toString()
        val p1 = Pattern.compile("(?<=\"items\":).+]")
        val m1 = p1.matcher(t)
        if (m1.find()) {
            val itemsInReciept = t.substring(m1.start(), m1.end())
            val namePattern = Pattern.compile("(?<=name\":)[^,}]+")
            val quantityPattern = Pattern.compile("(?<=quantity\":)[^,}]+")
            val pricePattern = Pattern.compile("(?<=price\":)[^,}]+")
            val nameMatcher = namePattern.matcher(itemsInReciept)
            val quantityMatcher = quantityPattern.matcher(itemsInReciept)
            val priceMatcher = pricePattern.matcher(itemsInReciept)


            while (nameMatcher.find() && quantityMatcher.find() && priceMatcher.find()) {
                val price = itemsInReciept.substring(priceMatcher.start(), priceMatcher.end())

                val name = itemsInReciept.substring(nameMatcher.start(), nameMatcher.end())

                val quantity =
                    itemsInReciept.substring(quantityMatcher.start(), quantityMatcher.end())
                parse.add(Product(name, price, quantity))

            }
        }
    }

    fun parseQr(str: String?): ArrayList<String> {
        val s: String
        if (str == null) {
            s = "t=20191123T1821&s=1496.64&fn=9280440300065001&i=57638&fp=3805453234&n=1"
        }
        else {
            s = str
        }
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
        val arr: ArrayList<String> = arrayListOf(
            "https://proverkacheka.nalog.ru:9999/v1/inns/*/kkts/*/fss/$fnNum/tickets/$iNum?fiscalSign=$fpNum&sendToEmail=no",
            "https://proverkacheka.nalog.ru:9999/v1/ofds/*/inns/*/fss/$fnNum/operations/1/tickets/$iNum?fiscalSign=$fpNum&date=$timeNum2&sum=$sumNum"
        )
        return arr
        }
    }