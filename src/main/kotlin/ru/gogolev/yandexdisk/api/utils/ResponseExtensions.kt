package ru.gogolev.yandexdisk.api.utils

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import ru.gogolev.yandexdisk.api.config.RestAssuredConfiguration

fun RestAssured.prepareRequest(restAssuredConfiguration: RestAssuredConfiguration, path: String) {
    given()
        .spec(restAssuredConfiguration.requestSpec)
        .`when`()
        .get(path)
        .then()
        .spec(restAssuredConfiguration.responseSpec)
}

inline fun <reified T> ValidatableResponse.extractAs(): T {
    return this.extract().body().`as`(T::class.java)
}