package com.google.credentialmanager.sample

import android.content.Context
import com.google.android.gms.fido.Fido

fun checkPlatformAuthenticatorAvailable(
    context: Context,
    callback: (Boolean, Exception?) -> Unit
) {
    val fido2ApiClient = Fido.getFido2ApiClient(context)
    fido2ApiClient.isUserVerifyingPlatformAuthenticatorAvailable
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val isSupported = task.result ?: false
                callback(isSupported, null) // Pass result as true/false with no exception
            } else {
                callback(false, task.exception) // Pass result as false with an exception
            }
        }
}