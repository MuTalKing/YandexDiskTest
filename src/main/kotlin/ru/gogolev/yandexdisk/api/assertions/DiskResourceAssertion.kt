package ru.gogolev.yandexdisk.api.assertions

import io.kotest.assertions.asClue
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.qameta.allure.Step
import ru.gogolev.yandexdisk.api.dto.DiskResourcesResponse
import ru.gogolev.yandexdisk.api.enum.DiskResourceParameters
import ru.gogolev.yandexdisk.api.enum.DiskResourceParameters.HREF
import ru.gogolev.yandexdisk.api.enum.DiskResourceParameters.METHOD
import ru.gogolev.yandexdisk.api.enum.DiskResourceParameters.TEMPLATED

class DiskResourceAssertion {
    @Step("Проверяем, что ответ содержит только те поля, которые указаны в параметре fields")
    fun checkDiskResourceParameters(fields: DiskResourceParameters, diskResourcesResponse: DiskResourcesResponse) {
        assertSoftly {
            when (fields) {
                METHOD -> diskResourcesResponse.asClue {
                    it.method shouldNotBe null
                    it.href shouldBe null
                    it.templated shouldBe null
                }

                HREF -> diskResourcesResponse.asClue {
                    it.href shouldNotBe null
                    it.method shouldBe null
                    it.templated shouldBe null
                }

                TEMPLATED -> diskResourcesResponse.asClue {
                    it.templated shouldNotBe null
                    it.href shouldBe null
                    it.method shouldBe null
                }
            }
        }
    }

    @Step("Проверяем, что все поля в ответе заполнены")
    fun checkResponseBody(diskResourcesResponse: DiskResourcesResponse) {
        assertSoftly {
            diskResourcesResponse.asClue {
                it.method shouldNotBe null
                it.href shouldNotBe null
                it.templated shouldNotBe null
            }
        }
    }
}