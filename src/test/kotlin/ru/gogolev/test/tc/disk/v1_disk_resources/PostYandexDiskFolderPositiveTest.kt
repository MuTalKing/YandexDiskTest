package ru.gogolev.test.tc.disk.v1_disk_resources

import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
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
@Feature("POST v1/disk/resources")
@Story("Позитивные тесты")
@SpringBootTest(classes = [TestConfig::class])
class PostYandexDiskFolderPositiveTest @Autowired constructor(
    private val yandexDiskService: YandexDiskService,
    private val diskResourceAssertion: DiskResourceAssertion
) {
    @ParameterizedTest(name = "c параметрами force_async = {0}")
    @MethodSource("testDataSource")
    @DisplayName("Сценарий: проверка создания копии папки")
    fun `copy folder successfully with parameters`(statusCode: Int, forceAsync: Boolean) {
        val folderName = engString(6)
        yandexDiskService.putDiskResource(params = mapOf("path" to folderName)).asClue {
            it.statusCode shouldBe 201
        }
        val secondFolderName = engString(6)
        val postResponse = yandexDiskService.postDiskResource(
            params = mapOf(
                "from" to folderName,
                "path" to secondFolderName,
                "force_async" to forceAsync.toString()
            )
        )
        postResponse.asClue {
            it.statusCode shouldBe statusCode
        }
        if (forceAsync) {
            diskResourceAssertion.checkResponseBody(postResponse.then().extractAs())
        }
    }

    @ParameterizedTest(name = "c параметром fields = {0}")
    @EnumSource(DiskResourceParameters::class)
    @DisplayName("Сценарий: проверка копирования папки ")
    fun `copy folder with fields parameter`(fields: DiskResourceParameters) {
        val folderName = engString(6)
        yandexDiskService.putDiskResource(params = mapOf("path" to folderName)).asClue {
            it.statusCode shouldBe 201
        }
        val secondFolderName = engString(6)
        val postResponse = yandexDiskService.postDiskResource(
            params = mapOf(
                "from" to folderName,
                "path" to secondFolderName,
                "fields" to fields.name.lowercase()
            )
        ).then().extractAs<DiskResourcesResponse>()
        diskResourceAssertion.checkDiskResourceParameters(fields = fields, diskResourcesResponse = postResponse)
    }

    companion object {
        @JvmStatic
        fun testDataSource() = Stream.of(
            Arguments.of(202, true),
            Arguments.of(201, false)
        )
    }
}