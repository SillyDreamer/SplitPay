package com.example.myapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapp.PresenterHolder
import com.example.myapp.contract.QrContract
import com.example.myapp.presenter.QrPresenter
import com.example.myapp.R
import com.example.myapp.model.Model
import kotlinx.android.synthetic.main.activity_qr.*



class QrActivity : AppCompatActivity(), QrContract.View {

    lateinit var presenter : QrPresenter

    var btn : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        presenter = (application as PresenterHolder).getQrPresenter()

        btn = findViewById(R.id.scan_check)

        btn!!.setOnClickListener {
            val permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
//                val intent = Intent(this@QrActivity, ScanActivity::class.java)
//                startActivity(intent)
                presenter.onButtonWasClicked(null)
                val intent = Intent(this, AddUserActivity::class.java)
                startActivity(intent)
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                    101)
            }
        }

        previous_check.setOnClickListener {
            val intent = Intent(this, PreviousCheckActivity::class.java)
            startActivity(intent)

        }
    }
}
