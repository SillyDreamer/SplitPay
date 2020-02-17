package com.example.myapp.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.PresenterHolder
import com.example.myapp.adapters.AdapterResult
import com.example.myapp.contract.ResultContract
import com.example.myapp.presenter.ResultPresenter
import com.example.myapp.R
import com.example.myapp.model.User
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.edit_dialog.view.*

class ResultActivity : AppCompatActivity(), ResultContract.view {

    lateinit var presenter: ResultPresenter
    lateinit var users: ArrayList<User>
    lateinit var adapter: AdapterResult
    var check_id: Long = 0

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        presenter = (application as PresenterHolder).getResultPresenter()

        check_id = intent.getLongExtra("check_id", 0)

        presenter.showUsers(check_id)


        recycleResult.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

    }

    override fun showUsers(array: ArrayList<User>) {
        users = array
        adapter = AdapterResult(
            users,
            listener = { listener(it) },
            listenerButton = { listenerButton(it) })
        recycleResult.adapter = adapter
    }

    private fun listenerButton(id: Int) {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.edit_dialog_pay, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mDialogView.ok_button.setOnClickListener {
            mAlertDialog.dismiss()
            val paid = mDialogView.edit.text.toString().toInt()
            users[id].paid = paid
            presenter.updateUser(users[id].name, users[id].money, paid, users[id].id, check_id)
            adapter.notifyDataSetChanged()
        }
        mDialogView.cancel_button.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun listener(id: Int) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hi, you owe me ${users[id].money}")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
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
