package ru.gogolev.yandexdisk.api.http

import io.qameta.allure.Step
import io.restassured.RestAssured
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import ru.gogolev.yandexdisk.api.config.RestAssuredConfiguration
import ru.gogolev.yandexdisk.api.utils.V1_DISK
import ru.gogolev.yandexdisk.api.utils.V1_DISK_RESOURCES
import ru.gogolev.yandexdisk.api.utils.V1_DISK_RESOURCES_COPY

class YandexDiskService(
    private val yandexDiskRestAssuredConfiguration: RestAssuredConfiguration
) {

    @Step("Получение информации о диске при запросе на ендпоинт {path}")
    fun getDiskInformation(): ValidatableResponse = RestAssured
        .given()
        .spec(yandexDiskRestAssuredConfiguration.requestSpec)
        .`when`()
        .get(V1_DISK)
        .then()
        .spec(yandexDiskRestAssuredConfiguration.responseSpec)

    @Step("Отправление запроса на получение информации о папке или файле")
    fun getDiskResource(params: Map<String, String>): Response = RestAssured
        .given()
        .spec(yandexDiskRestAssuredConfiguration.requestSpec)
        .queryParams(params)
        .`when`()
        .get(V1_DISK_RESOURCES)

    @Step("Отправление запроса на создание папки или файла")
    fun putDiskResource(path: String = V1_DISK_RESOURCES, params: Map<String, String>): Response = RestAssured
        .given()
        .spec(yandexDiskRestAssuredConfiguration.requestSpec)
        .queryParams(params)
        .`when`()
        .put(path)

    @Step("Отправление запроса на удаление папки или файла")
    fun deleteDiskResource(params: Map<String, String>): Response = RestAssured
        .given()
        .spec(yandexDiskRestAssuredConfiguration.requestSpec)
        .queryParams(params)
        .`when`()
        .delete(V1_DISK_RESOURCES)

    @Step("Отправление POST запроса на '/v1/disk/resources/copy'")
    fun postDiskResource(params: Map<String, String>): Response = RestAssured
        .given()
        .spec(yandexDiskRestAssuredConfiguration.requestSpec)
        .queryParams(params)
        .`when`()
        .post(V1_DISK_RESOURCES_COPY)

}