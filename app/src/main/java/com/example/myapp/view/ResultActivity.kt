package com.example.myapp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.PresenterHolder
import com.example.myapp.adapters.AdapterResult
import com.example.myapp.contract.ResultContract
import com.example.myapp.presenter.ResultPresenter
import com.example.myapp.R
import com.example.myapp.model.Model
import com.example.myapp.model.User
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity(), ResultContract.view {

    lateinit var presenter : ResultPresenter
    lateinit var users : ArrayList<User>

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        presenter = (application as PresenterHolder).getResultPresenter()

        val check_id = intent.getLongExtra("check_id", 0)


        recycleResult.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

    }

    fun showUsers(array : ArrayList<User>) {
        users = array
        recycleResult.adapter = AdapterResult(users) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Hi, you owe me ${users[it].money}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
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
