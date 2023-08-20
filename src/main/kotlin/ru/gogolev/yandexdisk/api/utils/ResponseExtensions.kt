package ru.gogolev.yandexdisk.api.utils

import io.restassured.response.ValidatableResponse

inline fun <reified T> ValidatableResponse.extractAs(): T {
    return this.extract().body().`as`(T::class.java)
}