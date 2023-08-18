package ru.gogolev.yandexdisk.api.assertions

import io.kotest.assertions.asClue
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.qameta.allure.Step
import ru.gogolev.yandexdisk.api.dto.Error

class ErrorAssertion {
    @Step("Проверка совпадения ошибки, полученной от сервиса, с ожидаемой ошибкой")
    fun check(actualError: Error, expectedError: Error) {
        assertSoftly {
            actualError.asClue { actualError ->
                actualError.description shouldBe expectedError.description
                actualError.error shouldBe expectedError.error
                actualError.message shouldBe expectedError.message
            }
        }
    }
}