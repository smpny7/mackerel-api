package com.mackerel.api.controller

import com.mackerel.api.model.JsonResponse
import com.mackerel.api.model.StatusStoreData
import com.mackerel.api.model.WebRequest
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class CommunicationController {
    @RequestMapping("/cron")
    fun setResult(): JsonResponse<StatusStoreData> {
        val url = "https://api.vivace-app.com/v4.0/license".toHttpUrl()
        val statusCode = WebRequest(url).getStatusCode()
        val isProtectedBySSL = WebRequest(url).getIsProtectedBySSL()
        val ttfb = WebRequest(url).getTTFB()
        val statusStoreData = StatusStoreData(statusCode, isProtectedBySSL, ttfb)
        return JsonResponse(true, statusStoreData)
    }
}
