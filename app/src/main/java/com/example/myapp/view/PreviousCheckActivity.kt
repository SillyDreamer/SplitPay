package com.example.myapp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.adapters.AdapterPreviousCheck
import com.example.myapp.R
import com.example.myapp.contract.PreviousCheckContract
import com.example.myapp.presenter.PreviousCheckPresenter
import kotlinx.android.synthetic.main.activity_previous_check.*

class PreviousCheckActivity : AppCompatActivity(), PreviousCheckContract.View {

    private val presenter = PreviousCheckPresenter(this)

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_check)

        val arr  = arrayListOf<Pair<Long, String>>(Pair(1, "1"))


        val adapter = AdapterPreviousCheck(presenter.showChecks()) {
            listener(it)
        }
        recycle_view_previous.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recycle_view_previous.adapter = adapter

    }

    private fun listener(id : Long) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("check_is", id)
        startActivity(intent)
    }
}
