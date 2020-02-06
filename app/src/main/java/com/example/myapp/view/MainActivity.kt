package com.example.myapp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.adapters.Adapter
import com.example.myapp.contract.MainContract
import com.example.myapp.model.Product
import com.example.myapp.presenter.MainPresenter
import com.example.myapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.view {

    private val presenter = MainPresenter(this)
    private var count = hashMapOf<Int, Int>()

    private var check_id  : Long = 0

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        check_id = intent.getLongExtra("check_id", 0)
        val users = presenter.showUsers(check_id)
        var products = presenter.showProducts(check_id)
        var adapter = Adapter(
            products,
            users
        ) { hashMap: HashMap<Pair<String, String>, ArrayList<CheckBox>>, list: List<Product>, i: Int ->
            listener(hashMap, list, i)
        }

        recycle_view_main.adapter = adapter
        recycle_view_main.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        add_check.setOnClickListener {
            presenter.addOneMoreCheck(null)
            products = presenter.showProducts(check_id)
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
}
