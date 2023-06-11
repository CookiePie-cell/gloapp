package com.salugan.gloapp.utils

import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class TrustAllCerts : X509TrustManager {
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        // No need to implement
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        // Accept all certificates
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
}

fun createTrustAllSSLSocketFactory(): SSLSocketFactory? {
    return try {
        val trustAllCerts = arrayOf<TrustManager>(TrustAllCerts())

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        sslContext.socketFactory
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        null
    } catch (e: KeyManagementException) {
        e.printStackTrace()
        null
    }
}