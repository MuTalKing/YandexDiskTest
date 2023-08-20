package ru.gogolev.test.tc.disk.v1_disk_resources

import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.gogolev.test.config.TestConfig
import ru.gogolev.yandexdisk.api.assertions.DiskResourceAssertion
import ru.gogolev.yandexdisk.api.dto.DiskResourcesResponse
import ru.gogolev.yandexdisk.api.enum.DiskResourceParameters
import ru.gogolev.yandexdisk.api.http.YandexDiskService
import ru.gogolev.yandexdisk.api.utils.engString
import ru.gogolev.yandexdisk.api.utils.extractAs
import java.util.stream.Stream


@Epic("Яндекс диск")
@Feature("DELETE v1/disk/resources")
@Story("Позитивные тесты")
@SpringBootTest(classes = [TestConfig::class])
class DeleteYandexDiskFolderPositiveTest @Autowired constructor(
    private val yandexDiskService: YandexDiskService,
    private val diskResourceAssertion: DiskResourceAssertion
) {
    @Test
    @DisplayName("Сценарий: удаление папки при помощи DELETE запроса на ендпоинт '/v1/disk/resources'")
    fun `delete folder successfully`() {
        val folderName = engString(6)
        yandexDiskService.putDiskResource(params = mapOf("path" to folderName)).asClue {
            it.statusCode shouldBe 201
        }
        val deleteResponse = yandexDiskService.deleteDiskResource(params = mapOf("path" to folderName))
        deleteResponse.asClue {
            it.statusCode shouldBe 204
        }
        diskResourceAssertion.checkResponseBody(deleteResponse.then().extractAs())
    }

    @ParameterizedTest(name = "c параметром fields = {0}")
    @EnumSource(DiskResourceParameters::class)
    @DisplayName("Сценарий: проверка удаления папки ")
    fun `put folder to yandex disk with fields parameter`(fields: DiskResourceParameters) {
        val folderName = engString(6)
        val deleteResponse = yandexDiskService.deleteDiskResource(
            params = mapOf(
                "path" to folderName,
                "fields" to fields.name.lowercase()
            )
        ).then().extractAs<DiskResourcesResponse>()
        diskResourceAssertion.checkDiskResourceParameters(fields = fields, diskResourcesResponse = deleteResponse)
    }

    @ParameterizedTest(name = "c параметрами force_async = {0} и permanently = {1}")
    @MethodSource("testDataSource")
    @DisplayName("Сценарий: проверка создания папки")
    fun `delete folder successfully with parameters`(statusCode: Int, forceAsync: Boolean, permanently: Boolean) {
        val folderName = engString(6)
        yandexDiskService.putDiskResource(params = mapOf("path" to folderName)).asClue {
            it.statusCode shouldBe 201
        }
        val deleteResponse = yandexDiskService.deleteDiskResource(
            params = mapOf(
                "path" to folderName,
                "force_async" to forceAsync.toString(),
                "permanently" to permanently.toString()
            )
        )
        deleteResponse.asClue {
            it.statusCode shouldBe statusCode
        }
        if (forceAsync) {
            diskResourceAssertion.checkResponseBody(deleteResponse.then().extractAs())
        }
    }

    companion object {
        @JvmStatic
        fun testDataSource() = Stream.of(
            Arguments.of(202, true, true),
            Arguments.of(204, false, true),
            Arguments.of(202, true, false),
            Arguments.of(204, false, false),
        )
    }
}