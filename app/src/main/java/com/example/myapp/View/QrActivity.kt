package com.example.myapp.View

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
import com.example.myapp.Contract.QrContract
import com.example.myapp.Presenter.QrPresenter
import com.example.myapp.R
import kotlinx.android.synthetic.main.activity_qr.*

class QrActivity : AppCompatActivity(), QrContract.View {

    val presenter : QrPresenter = QrPresenter(this)
    var btn : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        btn = findViewById(R.id.scan_check)

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

        var tvresult : String? = null
    }
}
