package com.example.myapp.presenter

import android.widget.CheckBox
import com.example.myapp.contract.MainContract
import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.Repository
import com.example.myapp.model.User
import com.example.myapp.utils.Runner
import com.example.myapp.view.MainActivity
import org.json.JSONObject
import java.util.regex.Pattern

class MainPresenter(val model : Model, val runner : Runner): MainContract.presenter {

    var mView : MainActivity? = null

    override fun addMoneyFromUser(users : ArrayList<User>, checkMap : HashMap<Pair<String, String>, ArrayList<CheckBox>>, check_id : Long) {
        runner.runInBackground(Runnable {
            val money = arrayListOf<Int>()
            val user = arrayListOf<String>()
            val id = arrayListOf<Long>()
            for (x in users) {
                user.add(x.name)
                money.add(0)
                id.add(x.id)
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
                model.updateUser(user[i], money[i], id[i], check_id)
            }
        })

    }

    override fun showData(check_id : Long) {

        runner.runInBackground(Runnable {
            val product = model.showProducts(check_id)
            val user = model.showUsers(check_id)
            runner.runOnMain(Runnable {if (mView != null) {
                mView?.showData(product, user)
            }  })
        })
    }

    //add one more check

    var parse: ArrayList<Product> = arrayListOf()


    override fun addOneMoreCheck(qrResult: String?) {
        runner.runInBackground(Runnable {
            val arr = parseQr(qrResult)
            val message = Repository().loadMessage(arr[0], arr[1])
            parseResult(message)
            for (product in parse) {
                model.addToDBProduct(product)
            }
        })

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

    fun attachView(view : MainActivity) {
        mView = view
    }

    fun detachView() {
        mView = null
    }


}