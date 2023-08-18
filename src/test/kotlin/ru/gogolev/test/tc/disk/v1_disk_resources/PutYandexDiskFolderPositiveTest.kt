package ru.gogolev.test.tc.disk.v1_disk_resources

import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.string
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.gogolev.test.config.TestConfig
import ru.gogolev.yandexdisk.api.assertions.DiskInformationAssertion
import ru.gogolev.yandexdisk.api.dto.PutResponse
import ru.gogolev.yandexdisk.api.enum.PutResponseParameters
import ru.gogolev.yandexdisk.api.enum.PutResponseParameters.*
import ru.gogolev.yandexdisk.api.generators.DiskInformationGenerator
import ru.gogolev.yandexdisk.api.http.YandexDiskService
import ru.gogolev.yandexdisk.api.utils.engString
import ru.gogolev.yandexdisk.api.utils.extractAs

@Epic("Яндекс диск")
@Feature("v1/disk/resources")
@Story("Позитивные тесты")
@SpringBootTest(classes = [TestConfig::class])
class PutYandexDiskFolderPositiveTest @Autowired constructor(
    private val yandexDiskService: YandexDiskService
) {
    @Test
    @DisplayName("Сценарий: проверка создания, получения и удаления папки на ендпоинте '/v1/disk/resources'")
    fun `put folder to yandex disk`() {
        val folderName = engString(6)
        yandexDiskService.putDiskResource(params = mapOf("path" to folderName)).asClue {
            it.statusCode shouldBe 201
        }
        yandexDiskService.getDiskResource(params = mapOf("path" to folderName)).asClue {
            it.statusCode shouldBe 200
        }
        yandexDiskService.deleteDiskResource(params = mapOf("path" to folderName)).asClue {
            it.statusCode shouldBe 204
        }
    }

    @ParameterizedTest(name = "c параметром fields = {0}")
    @EnumSource(PutResponseParameters::class)
    @DisplayName("Сценарий: проверка создания папки ")
    fun `put folder to yandex disk with fields parameter`(fields: PutResponseParameters) {
        val folderName = engString(6)
        val putResponse = yandexDiskService.putDiskResource(params = mapOf("path" to folderName, "fields" to fields.name.lowercase())).then().extractAs<PutResponse>()
        when(fields) {
            METHOD -> putResponse.apply {
                this.method shouldNotBe null
                href shouldBe null
                templated shouldBe null
            }
            HREF -> putResponse.apply {
                href shouldNotBe null
                this.method shouldBe null
                templated shouldBe null
            }
            TEMPLATED -> putResponse.apply {
                templated shouldNotBe null
                href shouldBe null
                this.method shouldBe null
            }
        }

        yandexDiskService.deleteDiskResource(mapOf("path" to folderName)).asClue {
            it.statusCode shouldBe 204
        }
    }
}