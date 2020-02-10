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
import com.example.myapp.model.Model
import com.example.myapp.model.User
import com.example.myapp.utils.RealRunner
import com.example.myapp.utils.Runner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.view {

    lateinit var presenter : MainPresenter
    private var count = hashMapOf<Int, Int>()
    lateinit var adapter : Adapter

    private var check_id  : Long = 0

    var users : ArrayList<User> = arrayListOf()
    var products : ArrayList<Product> = arrayListOf()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = (application as PresenterHolder).getMainPresenter()

        check_id = intent.getLongExtra("check_id", 0)
        presenter.showData(check_id)

        recycle_view_main.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        add_check.setOnClickListener {
            presenter.addOneMoreCheck(null)
            presenter.showData(check_id)
            val check = adapter.checkMap
            adapter = Adapter(
                products,
                users
            ) { hashMap: HashMap<Pair<String, String>, ArrayList<CheckBox>>, list: List<Product>, i: Int ->
                listener(hashMap, list, i)
            }
            adapter.checkMap = check
            recycle_view_main.adapter = adapter
        }

        but.setOnClickListener {
            presenter.addMoneyFromUser(users, adapter.checkMap, check_id)

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("check_id", check_id)
            startActivity(intent)
        }
    }



    fun listener(hashMap: HashMap<Pair<String, String>, ArrayList<CheckBox>>, list: List<Product>, i: Int) {
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
                }
                else {
                    if (cb.isChecked)
                        cb.isChecked = false
                }
            }
        }
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)

    }


    fun showData(pr : ArrayList<Product>, us : ArrayList<User>) {
        products = pr
        users = us
        println("users $us")
        adapter = Adapter(
            products,
            users
        ) { hashMap: HashMap<Pair<String, String>, ArrayList<CheckBox>>, list: List<Product>, i: Int ->
            listener(hashMap, list, i)
        }

        recycle_view_main.adapter = adapter
    }
}
