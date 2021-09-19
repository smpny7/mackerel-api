package com.mackerel.api.model

data class JsonResponse<T>(
    val success: Boolean,
    val data: T
)