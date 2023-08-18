package ru.gogolev.yandexdisk.api.config

import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.LogDetail
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import io.restassured.specification.ResponseSpecification

class RestAssuredConfiguration(
    private val url: String,
    private val oAuthToken: String
) {

    private val logConfig = LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
    private val config = RestAssuredConfig.config().logConfig(logConfig)

    val requestSpec: RequestSpecification = RequestSpecBuilder()
        .setBaseUri(url)
        .addHeader("Accept", "application/json")
        .addHeader("Authorization", " OAuth $oAuthToken")
        .setContentType(ContentType.JSON)
        .setConfig(config)
        .log(LogDetail.URI)
        .build()

    val responseSpec: ResponseSpecification = ResponseSpecBuilder()
        .log(LogDetail.BODY)
        .build()
}