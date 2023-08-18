package ru.gogolev.test.tc.disk.v1_disk

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.gogolev.test.config.TestConfig
import ru.gogolev.yandexdisk.api.assertions.DiskInformationAssertion
import ru.gogolev.yandexdisk.api.dto.DiskInformation
import ru.gogolev.yandexdisk.api.generators.DiskInformationGenerator
import ru.gogolev.yandexdisk.api.http.YandexDiskService
import ru.gogolev.yandexdisk.api.utils.extractAs

@Epic("Яндекс диск")
@Feature("v1/disk")
@Story("Позитивные тесты")
@SpringBootTest(classes = [TestConfig::class])
class GetYandexDiskInformationTest @Autowired constructor(
    private val yandexDiskService: YandexDiskService,
    private val diskInformationGenerator: DiskInformationGenerator,
    private val diskInformationAssertion: DiskInformationAssertion
) {

    @Test
    @DisplayName("Сценарий: Проверка ответа на GET запрос по ендпоинту=/v1/disk")
    fun `check get request v1_disk`() {
        val expectedDiskInformation = diskInformationGenerator.createDiskInformationForGembelSerUser()
        val actualDiskInformation = yandexDiskService.getDiskInformation().extractAs<DiskInformation>()
        diskInformationAssertion.check(actualDiskInformation, expectedDiskInformation)
    }


}