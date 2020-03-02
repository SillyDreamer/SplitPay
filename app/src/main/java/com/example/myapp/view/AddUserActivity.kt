package com.example.myapp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.PresenterHolder
import com.example.myapp.adapters.AdapterAddUser
import com.example.myapp.contract.AddUserContract
import com.example.myapp.model.User
import com.example.myapp.presenter.AddUserPresenter
import com.example.myapp.R
import kotlinx.android.synthetic.main.activity_add_user.*

class AddUserActivity : AppCompatActivity(), AddUserContract.view {

    private val listUser = arrayListOf<User>()
    lateinit var presenter: AddUserPresenter

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        presenter = (application as PresenterHolder).getAddUserPresenter()
        val adapter = AdapterAddUser(listUser)
        recycle_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recycle_view.adapter = adapter

        add.setOnClickListener {
            if (editText.text.toString().isNotEmpty()) {
                if (editText.text.toString().isDigitsOnly()) {
                    val num = editText.text.toString().toInt()
                    for (x in 1..num) {
                        presenter.addButton("P$x", 0)
                        listUser.add(User("P$x", 0, 0.0))
                    }
                    adapter.notifyDataSetChanged()
                    editText.setText("")
                } else {
                    presenter.addButton(editText.text.toString(), 0)
                    listUser.add(User(editText.text.toString(), 0, 0.0))
                    adapter.notifyDataSetChanged()
                    editText.setText("")
                }
            }
        }

        button_next.setOnClickListener {
            presenter.lastCheckId()
        }
    }

    override fun showCheckId(id: Long) {
        val intent = Intent(this, MainActivity::class.java)
        println("check id $id")
        intent.putExtra("check_id", id)
        startActivity(intent)
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
