package com.kuro.chitchat.auth_core

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity


@KMPAuthInternalApi
fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}