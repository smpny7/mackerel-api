package com.mackerel.api.controller

import com.mackerel.api.model.JsonResponse
import com.mackerel.api.model.WebRequest
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class CommunicationController {
    @RequestMapping("/cron")
    fun setResult(): JsonResponse<String?> {
        val webRequest = WebRequest()
        val contents = webRequest.getTTFB("https://coalabo.net")
        return JsonResponse(true, contents)
    }
}
