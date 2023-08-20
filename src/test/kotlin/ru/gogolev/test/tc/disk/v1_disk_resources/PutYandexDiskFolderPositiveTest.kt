package ru.gogolev.test.tc.disk.v1_disk_resources

import io.kotest.assertions.asClue
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.gogolev.test.config.TestConfig
import ru.gogolev.yandexdisk.api.assertions.DiskResourceAssertion
import ru.gogolev.yandexdisk.api.dto.DiskResourcesResponse
import ru.gogolev.yandexdisk.api.enum.DiskResourceParameters
import ru.gogolev.yandexdisk.api.http.YandexDiskService
import ru.gogolev.yandexdisk.api.utils.engString
import ru.gogolev.yandexdisk.api.utils.extractAs

@Epic("Яндекс диск")
@Feature("PUT v1/disk/resources")
@Story("Позитивные тесты")
@SpringBootTest(classes = [TestConfig::class])
class PutYandexDiskFolderPositiveTest @Autowired constructor(
    private val yandexDiskService: YandexDiskService,
    private val diskResourceAssertion: DiskResourceAssertion,
) {
    @Test
    @DisplayName("Сценарий: проверка создания, получения и удаления папки на ендпоинте '/v1/disk/resources'")
    fun `put folder to yandex disk`() {
        val folderName = engString(6)
        val putResponse = yandexDiskService.putDiskResource(params = mapOf("path" to folderName))
        putResponse.asClue {
            it.statusCode shouldBe 201
        }
        assertSoftly {
            putResponse.then().extractAs<DiskResourcesResponse>().asClue {
                it.method shouldNotBe null
                it.href shouldNotBe null
                it.templated shouldNotBe null
            }
        }
        yandexDiskService.getDiskResource(params = mapOf("path" to folderName)).asClue {
            it.statusCode shouldBe 200
        }
        yandexDiskService.deleteDiskResource(params = mapOf("path" to folderName)).asClue {
            it.statusCode shouldBe 204
        }
    }

    @ParameterizedTest(name = "c параметром fields = {0}")
    @EnumSource(DiskResourceParameters::class)
    @DisplayName("Сценарий: проверка создания папки ")
    fun `put folder to yandex disk with fields parameter`(fields: DiskResourceParameters) {
        val folderName = engString(6)
        val putResponse =
            yandexDiskService.putDiskResource(params = mapOf("path" to folderName, "fields" to fields.name.lowercase()))
                .then().extractAs<DiskResourcesResponse>()
        diskResourceAssertion.checkDiskResourceParameters(fields = fields, diskResourcesResponse = putResponse)

        yandexDiskService.deleteDiskResource(mapOf("path" to folderName)).asClue {
            it.statusCode shouldBe 204
        }
    }
}