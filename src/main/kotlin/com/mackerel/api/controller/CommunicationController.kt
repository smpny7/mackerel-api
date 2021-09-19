package com.mackerel.api.controller

import com.mackerel.api.model.FirestoreProcess
import com.mackerel.api.model.JsonResponse
import com.mackerel.api.model.Status
import com.mackerel.api.model.WebRequest
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CommunicationController {
    @RequestMapping("/cron")
    fun setResult(): JsonResponse<String?> {
        val servers = FirestoreProcess().getActiveServers()
        for (server in servers) {
            val url = server.url.toHttpUrl()
            val webRequest = WebRequest(url)
            if (webRequest.getIsSuccessful()) {
                val ttfb = webRequest.getTTFB()
                val statusCode = webRequest.getStatusCode()
                val isProtectedBySSL = webRequest.getIsProtectedBySSL()
                val status = Status(true, statusCode, isProtectedBySSL, ttfb)
                FirestoreProcess().setStatus(server.id, status)
            } else {
                val status = Status(success = false)
                FirestoreProcess().setStatus(server.id, status)
            }
        }
        return JsonResponse(true, null)
    }
}
