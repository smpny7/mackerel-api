package com.mackerel.api.model

import okhttp3.Call
import okhttp3.EventListener
import okhttp3.Response
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class WebRequestEventsListener : EventListener() {
    var callStartAt: LocalDateTime = LocalDateTime.now()
    var responseHeadersEndAt: LocalDateTime = LocalDateTime.now()
    override fun callStart(call: Call) {
        this.callStartAt = LocalDateTime.now()
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        this.responseHeadersEndAt = LocalDateTime.now()
    }

    fun getTTFB(): Long {
        return ChronoUnit.MILLIS.between(this.callStartAt, this.responseHeadersEndAt)
    }
}