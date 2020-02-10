package com.example.myapp.utils

interface Runner {
    fun runInBackground(task: Runnable)
    fun runOnMain(task: Runnable)
}