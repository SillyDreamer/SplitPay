package com.example.myapp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.PresenterHolder
import com.example.myapp.adapters.AdapterPreviousCheck
import com.example.myapp.R
import com.example.myapp.contract.PreviousCheckContract
import com.example.myapp.model.Model
import com.example.myapp.presenter.PreviousCheckPresenter
import kotlinx.android.synthetic.main.activity_previous_check.*

class PreviousCheckActivity : AppCompatActivity(), PreviousCheckContract.View {

    lateinit var presenter : PreviousCheckPresenter

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_check)

        presenter = (application as PresenterHolder).getPreviousCheckPresenter()


        recycle_view_previous.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


    }

    private fun listener(id : Long) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("check_id", id)
        startActivity(intent)
    }

    fun showChecks(array : ArrayList<Pair<Long, String>>) {
        val adapter = AdapterPreviousCheck(array) {
            listener(it)
        }
        recycle_view_previous.adapter = adapter
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

    override fun onStart() {
        presenter.attachView(this)
        super.onStart()
    }
}
