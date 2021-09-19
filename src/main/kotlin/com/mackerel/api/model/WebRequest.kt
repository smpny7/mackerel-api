package com.mackerel.api.model

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class WebRequest(private val url: HttpUrl) {
    private val response: Response?
    private val webRequest = WebRequestEventsListener()

    init {
        val client = OkHttpClient.Builder()
            .eventListener(webRequest)
            .build()
        val request: Request = Request.Builder()
            .url(this.url)
            .build()

        this.response = runCatching {
            client.newCall(request).execute()
        }.fold(
            onSuccess = { it },
            onFailure = { null }
        )
    }

    fun getIsSuccessful(): Boolean {
        return this.response != null
    }

    fun getStatusCode(): Number {
        return this.response!!.code
    }

    fun getIsProtectedBySSL(): Boolean {
        return this.response!!.request.url.isHttps
    }

    fun getTTFB(): Number {
        this.response.use { return webRequest.getTTFB() }
    }
}