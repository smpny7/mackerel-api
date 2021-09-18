package com.mackerel.api.model

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class WebRequest(private val url: HttpUrl) {
    fun getStatusCode(): Number {
        val client = OkHttpClient.Builder()
            .build()
        val request: Request = Request.Builder()
            .url(this.url)
            .build()

        return client.newCall(request).execute().code
    }

    fun getIsProtectedBySSL(): Boolean {
        val client = OkHttpClient.Builder()
            .build()
        val request: Request = Request.Builder()
            .url(this.url)
            .build()
        val response = client.newCall(request).execute()

        return response.request.url.isHttps
    }

    fun getTTFB(): Number {
        val webRequest = WebRequestEventsListener()
        val client = OkHttpClient.Builder()
            .eventListener(webRequest)
            .build()
        val request: Request = Request.Builder()
            .url(this.url)
            .build()

        client.newCall(request).execute().use { return webRequest.getTTFB() }
    }
}