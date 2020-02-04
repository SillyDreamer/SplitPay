package com.example.myapp.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.AdapterPreviousCheck
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

        val adapter = AdapterPreviousCheck(presenter.showChecks())
        recycle_view_previous.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?
        recycle_view_previous.adapter = adapter

    }
}
