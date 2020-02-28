package com.example.myapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapp.PresenterHolder
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null
    var key = 0

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner View
        setContentView(mScannerView)

        key = intent.getIntExtra("key", 0)
        // Set the scanner View as the content View
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

    override fun handleResult(rawResult: Result) {
        // Do something with the result here
        Log.v("tag", rawResult.getText()) // Prints scan results
        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)


        val presenter = (application as PresenterHolder).getScanPresenter()
        presenter.attachView(this)
        val presenter2 = (application as PresenterHolder).getMainPresenter()



        println("key $key")
        if (key == 2) {
            if (!presenter.onButtonWasClicked(rawResult.text)) {
                Toast.makeText(this, "wrong qr", Toast.LENGTH_LONG).show()
                presenter.detachView()
                onBackPressed()
            } else {
                val intent = Intent(this, AddUserActivity::class.java)
                startActivity(intent)
            }
        } else if (key == 1) {
            if (!presenter2.addOneMoreCheck(rawResult.text)) {
                Toast.makeText(this, "wrong qr", Toast.LENGTH_LONG).show()
            }
            onBackPressed()
        }
    }
}
