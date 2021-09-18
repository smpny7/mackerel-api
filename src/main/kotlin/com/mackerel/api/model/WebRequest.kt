package com.mackerel.api.model

import okhttp3.OkHttpClient
import okhttp3.Request

class WebRequest {
    fun getStatusCode(url: String): Number {
        val client = OkHttpClient.Builder()
            .build()
        val request: Request = Request.Builder()
            .url(url)
            .build()

        return client.newCall(request).execute().code()
    }

    fun getTTFB(url: String): String {
        val webRequest = WebRequestEventsListener()
        val client = OkHttpClient.Builder()
            .eventListener(webRequest)
            .build()
        val request: Request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { return "TTFB: ${webRequest.getTTFB()}ms" }
    }
}