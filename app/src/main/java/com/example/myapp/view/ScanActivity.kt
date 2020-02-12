package com.example.myapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapp.PresenterHolder
import com.example.myapp.presenter.QrPresenter
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null
    var key = 0
   // val presenter : QrPresenter = QrPresenter(this)

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)

        key = intent.getIntExtra("key", 0)
        // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult : Result) {
        // Do something with the result here
        Log.v("tag", rawResult.getText()) // Prints scan results
        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        val presenter = (application as PresenterHolder).getQrPresenter()
        val presenter2= (application as PresenterHolder).getMainPresenter()



        println("key $key")
        if (key == 2) {
            presenter.onButtonWasClicked("t=20191123T1821&s=1496.64&fn=9280440300065001&i=57638&fp=3805453234&n=1")
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }


        else if (key == 1) {
            presenter2.addOneMoreCheck(rawResult.text)
            onBackPressed()
        }

        //presenter.onButtonWasClicked(rawResult.text)






        //QrActivity.tvresult = rawResult.text
        //onBackPressed()

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}
