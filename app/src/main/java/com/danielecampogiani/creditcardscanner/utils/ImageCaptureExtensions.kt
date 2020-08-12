package com.danielecampogiani.creditcardscanner.utils

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun ImageCapture.takePicture(executor: Executor): ImageProxy {

    return suspendCoroutine { continuation ->
        takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {

            override fun onCaptureSuccess(image: ImageProxy) {
                continuation.resume(image)
                super.onCaptureSuccess(image)
            }

            override fun onError(exception: ImageCaptureException) {
                continuation.resumeWithException(exception)
                super.onError(exception)
            }
        })
    }


}