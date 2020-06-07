package com.salmanseifian.plash.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException

class RefreshDataWorker(
    appContext: Context,
    params: WorkerParameters
) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "info.salmanseifian.numbers.work.RefreshDataWorker"
    }


    override suspend fun doWork(): Result {
        try {
            for (i in 1..100) {
            }
        } catch (e: HttpException) {
            return Result.retry()
        }
        return Result.success()
    }
}