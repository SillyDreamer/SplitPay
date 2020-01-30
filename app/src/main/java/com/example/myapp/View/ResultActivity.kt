package com.example.myapp.View

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.AdapterResult
import com.example.myapp.Contract.ResultContract
import com.example.myapp.Presenter.ResultPresenter
import com.example.myapp.R
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.list_result.*

class ResultActivity : AppCompatActivity(), ResultContract.view {

    private var presenter = ResultPresenter(this)

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        recycleResult.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recycleResult.adapter = AdapterResult(presenter.showUsers()) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

    }
}
