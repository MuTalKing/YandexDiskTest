package ru.gogolev.yandexdisk.api.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
data class PutResponse(
    val href: String?,
    val method: String?,
    val templated: String?
)
