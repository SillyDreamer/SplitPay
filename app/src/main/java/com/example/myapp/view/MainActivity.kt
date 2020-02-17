package com.example.myapp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.PresenterHolder
import com.example.myapp.adapters.Adapter
import com.example.myapp.contract.MainContract
import com.example.myapp.model.Product
import com.example.myapp.presenter.MainPresenter
import com.example.myapp.R
import com.example.myapp.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    lateinit var presenter: MainPresenter
    private var count = hashMapOf<Int, Int>()
    lateinit var adapter: Adapter

    private var check_id: Long = 0

    var users: ArrayList<User> = arrayListOf()
    var products: ArrayList<Product> = arrayListOf()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = (application as PresenterHolder).getMainPresenter()

        check_id = intent.getLongExtra("check_id", 0)
        presenter.showData(check_id)

        recycle_view_main.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        add_check.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            intent.putExtra("key", 1)
            startActivity(intent)

            //Presenter.addOneMoreCheck("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1")

        }

        but.setOnClickListener {

            val money = arrayListOf<Int>()
            val user = arrayListOf<String>()
            val id = arrayListOf<Long>()
            for (x in users) {
                user.add(x.name)
                money.add(0)
                id.add(x.id)
            }
            for ((k, v) in adapter.checkMap) {
                var i = 0
                for (x in v) {
                    if (x.isChecked)
                        i++
                }
                for (x in v) {
                    if (x.isChecked) {
                        money[user.indexOf(x.text.toString())] =
                            money[user.indexOf(x.text.toString())] + k.second.toInt() / i
                    }
                }
            }

            presenter.addMoneyFromUser(user, money, id, check_id)


            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("check_id", check_id)
            startActivity(intent)
        }
    }


    override fun addCheck() {
        val check = adapter.checkMap
        presenter.showData(check_id)
        adapter = Adapter(
            products,
            users
        ) { hashMap: HashMap<Pair<String, String>, ArrayList<CheckBox>>, list: List<Product>, i: Int ->
            listener(hashMap, list, i)
        }
        adapter.checkMap = check
        recycle_view_main.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    private fun listener(
        hashMap: HashMap<Pair<String, String>, ArrayList<CheckBox>>,
        list: List<Product>,
        i: Int
    ) {
        if (count.containsKey(i))
            count[i] = count[i]!! + 1
        else
            count[i] = 1
        val arr = hashMap.get(Pair(list[i].name, list[i].price))
        if (arr != null) {
            for (cb in arr) {
                if (count[i]!! % 2 == 1) {
                    if (!cb.isChecked)
                        cb.isChecked = true
                } else {
                    if (cb.isChecked)
                        cb.isChecked = false
                }
            }
        }
    }

    override fun showData(pr: ArrayList<Product>, us: ArrayList<User>) {
        products = pr
        users = us
        adapter = Adapter(
            products,
            users
        ) { hashMap: HashMap<Pair<String, String>, ArrayList<CheckBox>>, list: List<Product>, i: Int ->
            listener(hashMap, list, i)
        }

        recycle_view_main.adapter = adapter
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)

    }

}
