package com.example.myapp.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors


class RealRunner : Runner {

    private val mExecutor = Executors.newSingleThreadExecutor()
    private val mHandler = Handler(Looper.getMainLooper())


    override fun runInBackground(task: Runnable) {
        mExecutor.submit(task)
    }

    override fun runOnMain(task: Runnable) {
        mHandler.post(task)
    }
}