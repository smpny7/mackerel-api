package com.mackerel.api.model

import com.google.cloud.Timestamp

data class Status(
    val success: Boolean,
    val statusCode: Number? = null,
    val isProtectedBySSL: Boolean? = null,
    val ttfb: Number? = null,
    val createdAt: Timestamp = Timestamp.now()
)