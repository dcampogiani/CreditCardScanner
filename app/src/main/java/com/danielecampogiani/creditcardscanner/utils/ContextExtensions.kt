package com.danielecampogiani.creditcardscanner.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).apply {
            addListener(Runnable {
                continuation.resume(get())
            }, ContextCompat.getMainExecutor(this@getCameraProvider))
        }
    }

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)

fun Context.hasPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED