package com.example.myapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.PresenterHolder
import com.example.myapp.adapters.AdapterPreviousCheck
import com.example.myapp.R
import com.example.myapp.contract.PreviousCheckContract
import com.example.myapp.presenter.PreviousCheckPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_previous_check.*
import kotlinx.android.synthetic.main.edit_dialog.view.*

class PreviousCheckActivity : AppCompatActivity(), PreviousCheckContract.View {

    lateinit var presenter: PreviousCheckPresenter
    lateinit var adapter: AdapterPreviousCheck
    lateinit var arr: ArrayList<Triple<String, String, String>>

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_check)

        fab.setOnClickListener { view ->
            val permissionStatus =
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(this, ScanActivity::class.java)
                intent.putExtra("key", 2)
                startActivity(intent)
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.CAMERA),
                    101
                )
            }
        }

        presenter = (application as PresenterHolder).getPreviousCheckPresenter()
        //presenter.showChecks()


        recycle_view_previous.layoutManager =
            LinearLayoutManager(this, LinearLayout.VERTICAL, false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 101) {
            val intent = Intent(this, ScanActivity::class.java)
            intent.putExtra("key", 2)
            startActivity(intent)
        }
    }

    private fun listener(id: Long) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("check_id", id)
        startActivity(intent)
    }

    private fun listenerButton(id: Int) {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.edit_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mDialogView.ok_button.setOnClickListener {
            mAlertDialog.dismiss()
            val name = mDialogView.edit.text.toString()
            arr[id] = Triple(arr[id].first, name, arr[id].third)

            presenter.updateCheck(name, arr[id].third, arr[id].first.toLong())
            adapter.notifyDataSetChanged()
        }
        mDialogView.cancel_button.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    override fun showChecks(array: ArrayList<Triple<String, String, String>>) {
        arr = array
        if (arr.isEmpty()) {
            helpMessage.visibility = VISIBLE
        }
        adapter = AdapterPreviousCheck(
            arr,
            listener = { listener(it) },
            listener2 = { listenerButton(it) })
        recycle_view_previous.adapter = adapter
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

    override fun onStart() {
        presenter.attachView(this)
        presenter.showChecks()
        super.onStart()
    }

}
