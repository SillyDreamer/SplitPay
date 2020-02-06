package com.example.myapp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.adapters.AdapterAddUser
import com.example.myapp.contract.AddUserContract
import com.example.myapp.model.User
import com.example.myapp.presenter.AddUserPresenter
import com.example.myapp.R
import com.example.myapp.model.Model
import kotlinx.android.synthetic.main.activity_add_user.*

class AddUserActivity : AppCompatActivity(), AddUserContract.view {

    private val listUser = arrayListOf<User>()
    private val model = Model(this)
    private val presenter = AddUserPresenter(model)

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val adapter = AdapterAddUser(listUser)
        recycle_view.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recycle_view.adapter = adapter

        add.setOnClickListener {
            presenter.addButton(editText.text.toString(), 0)
            listUser.add(User(editText.text.toString(), 0))
            adapter.notifyDataSetChanged()
            editText.setText("")
        }

        button_next.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("check_id", presenter.lastCheckId())
            startActivity(intent)
        }
    }
}
