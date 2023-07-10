package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.R

class BlurWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork() = try {
        val context = applicationContext
        makeStatusNotification("Blurring image", context)
        val picture = BitmapFactory.decodeResource(context.resources, R.drawable.android_cupcake)
        val converted = blurBitmap(picture, context)
        val fileUri = writeBitmapToFile(context, converted)
        makeStatusNotification("Generated image at $fileUri", context)
        Result.success()
    } catch (err: Throwable) {
        Log.e(TAG, err.stackTraceToString())
        Result.failure()
    }

    companion object {
        const val TAG = "BlurWorker"
    }
}
