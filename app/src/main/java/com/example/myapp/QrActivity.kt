package com.example.myapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_qr.*

class QrActivity : AppCompatActivity(), QrContract.View {

    val presenter : QrPresenter = QrPresenter(this)
    var btn : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        tvresult = findViewById(R.id.qr_result)

        next.setOnClickListener {
            Log.d("123", "onCreateActivity")
            presenter.onButtonWasClicked(null)
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }

        btn = findViewById(R.id.scan)

        btn!!.setOnClickListener {
            val permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(this@QrActivity, ScanActivity::class.java)
                startActivity(intent)
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                    101)
            }
        }
    }



    companion object {

        var tvresult: TextView? = null
    }
}
