package com.example.myapp.utils

class StubRunner: Runner {
    override fun runInBackground(task: Runnable) {
        task.run()
    }

    override fun runOnMain(task: Runnable) {
        task.run()
    }
}