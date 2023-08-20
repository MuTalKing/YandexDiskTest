package ru.gogolev.test.tc.disk.v1_disk_resources

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.gogolev.test.config.TestConfig
import ru.gogolev.yandexdisk.api.assertions.ErrorAssertion
import ru.gogolev.yandexdisk.api.dto.Error
import ru.gogolev.yandexdisk.api.generators.ErrorGenerator
import ru.gogolev.yandexdisk.api.http.YandexDiskService
import ru.gogolev.yandexdisk.api.utils.engString
import ru.gogolev.yandexdisk.api.utils.extractAs

@Epic("Яндекс диск")
@Feature("PUT v1/disk/resources")
@Story("Негативные тесты")
@SpringBootTest(classes = [TestConfig::class])
class PutYandexDiskFolderNegativeTest @Autowired constructor(
    private val yandexDiskService: YandexDiskService,
    private val errorGenerator: ErrorGenerator,
    private val errorAssertion: ErrorAssertion
) {
    @Test
    @DisplayName("Сценарий: проверка невалидного ендпоинта")
    fun `check invalid endpoint`() {
        val folderName = engString(6)
        val error = errorGenerator.generate404Error()
        val response =
            yandexDiskService.putDiskResource(path = "/v1/disk/resources1", params = mapOf("path" to folderName)).then()
                .extractAs<Error>()
        errorAssertion.check(actualError = response, expectedError = error)
    }

    @Test
    @DisplayName("Сценарий: проверка некорректного формата диска")
    fun `check invalid disk format exception`() {
        val folderName = engString(6)
        val error = errorGenerator.generate400Error(folderName)
        val response =
            yandexDiskService.putDiskResource(params = mapOf("path" to "D:$folderName")).then().extractAs<Error>()
        errorAssertion.check(actualError = response, expectedError = error)
    }

    @Test
    @DisplayName("Сценарий: проверка несуществующего пути")
    fun `check disk path doesnt exist exception`() {
        val folderName = "D/${engString(6)}"
        val error = errorGenerator.generate409Error(folderName)
        val response = yandexDiskService.putDiskResource(params = mapOf("path" to folderName)).then().extractAs<Error>()
        errorAssertion.check(actualError = response, expectedError = error)
    }
}