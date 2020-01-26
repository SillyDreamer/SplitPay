package com.example.myapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.view {

    private val presenter = MainPresenter(this)

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val users = presenter.showUsers()
        val adapter = Adapter(presenter.showProducts(), users)
        recycle_view_main.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recycle_view_main.adapter = adapter


        but.setOnClickListener {
            presenter.addMoneyFromUser(users, adapter.checkMap)

            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
    }
}
