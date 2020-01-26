package com.example.myapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.android.synthetic.main.activity_qr.*

class AddUserActivity : AppCompatActivity(), AddUserContract.view {

    private val listUser = arrayListOf<User>()
    private val presenter = AddUserPresenter(this)

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val adapter = AdapterAddUser(listUser)
        recycle_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recycle_view.adapter = adapter

        add.setOnClickListener {
            listUser.add(User(editText.text.toString(), 0))
            presenter.addButton(editText.text.toString(), 0)
            adapter.notifyDataSetChanged()
            editText.setText("")
        }

        button_next.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
