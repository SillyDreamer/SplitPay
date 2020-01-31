package com.example.myapp.presenter

import android.content.Context
import android.os.AsyncTask
import android.widget.CheckBox
import com.example.myapp.contract.MainContract
import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.Repository
import com.example.myapp.model.User
import org.json.JSONObject
import java.util.regex.Pattern

class MainPresenter(val context : Context): MainContract.presenter {
    var t1 : Thread? = null
    override fun addMoneyFromUser(users : ArrayList<User>, checkMap : HashMap<Pair<String, String>, ArrayList<CheckBox>>) {

        val runnable  = Runnable {
            val money = arrayListOf<Int>()
            val user = arrayListOf<String>()
            for (x in users) {
                user.add(x.name)
                money.add(0)
            }
            for ((k , v) in checkMap) {
                var i = 0
                for (x in v) {
                    if (x.isChecked)
                        i++
                }
                for (x in v) {
                    if (x.isChecked) {
                        money[user.indexOf(x.text.toString())] = money[user.indexOf(x.text.toString())] +  k.second.toInt() / i
                    }
                }
            }
            for (i in 0 until user.size) {
                model.updateUser(user[i], money[i], i+1)
            } }

       Thread(runnable).start()

    }

    private val model = Model(context)

    override fun showUsers(): ArrayList<User> {
//        if (t1 != null)
//            t1!!.join()
        val task = UserAsyncTask().execute()
        return task.get()
    }


    override fun showProducts(): ArrayList<Product> {
        if (t1 != null)
            t1!!.join()
        val task = ProductAsyncTask().execute()
        return task.get()
    }

    inner class ProductAsyncTask : AsyncTask<String, String, ArrayList<Product>>() {
        override fun doInBackground(vararg params: String?): ArrayList<Product> {
            return model.showProducts()
        }

    }


    inner class UserAsyncTask : AsyncTask<String, String, ArrayList<User>>() {
        override fun doInBackground(vararg params: String?): ArrayList<User> {
            return model.showUsers()
        }

    }



    //add one more check

    var parse: ArrayList<Product> = arrayListOf()


    override fun addOneMoreCheck(qrResult: String?) {

        val runnable = Runnable {
            val model = Model(context)
            val arr = parseQr(qrResult)
            val message = Repository().loadMessage(arr[0], arr[1])
            parseResult(message)
            //model.dropTable()
            for (product in parse) {
                model.addToDBProduct(product)
            }
        }
        t1 = Thread(runnable)
        t1!!.start()

    }

    private fun parseResult(content: String) {
        println(content)
        val test = JSONObject(content).getJSONObject("document").getJSONObject("receipt").getJSONArray("items")

        test.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } }
            .map { parse.add(Product(it.get("name").toString(), it.get("price").toString(), it.get("quantity").toString()))}
    }

    private fun parseQr(str: String?): ArrayList<String> {
        val s: String = str ?: "t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1"
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