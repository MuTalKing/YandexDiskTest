package ru.gogolev.yandexdisk.api.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DiskResourcesResponse(
    val href: String?,
    val method: String?,
    val templated: String?
)