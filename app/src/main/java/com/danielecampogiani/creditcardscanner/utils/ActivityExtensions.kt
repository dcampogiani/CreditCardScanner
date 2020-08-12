package com.danielecampogiani.creditcardscanner.utils

import androidx.activity.ComponentActivity
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

fun ComponentActivity.launchWhenResumed(block: suspend CoroutineScope.() -> Unit): Job =
    lifecycle.coroutineScope.launchWhenResumed(block)
