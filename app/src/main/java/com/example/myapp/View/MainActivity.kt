package com.example.myapp.View

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.Adapter
import com.example.myapp.Contract.MainContract
import com.example.myapp.Model.Product
import com.example.myapp.Presenter.MainPresenter
import com.example.myapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.view {

    private val presenter = MainPresenter(this)
    private var count = 0

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val users = presenter.showUsers()
        val adapter = Adapter(presenter.showProducts(), users) { hashMap: HashMap<Pair<String, String>, ArrayList<CheckBox>>, list: List<Product>, i: Int ->
            count++
            val arr = hashMap.get(Pair(list[i].name, list[i].price))
            if (arr != null) {
                for (cb in arr) {
                    if (count % 2 == 1) {
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
        recycle_view_main.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recycle_view_main.adapter = adapter

        but.setOnClickListener {
            presenter.addMoneyFromUser(users, adapter.checkMap)

            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
    }
}
