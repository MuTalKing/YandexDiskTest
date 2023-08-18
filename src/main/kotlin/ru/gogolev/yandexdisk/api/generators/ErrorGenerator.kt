package ru.gogolev.yandexdisk.api.generators

import io.qameta.allure.Step
import ru.gogolev.yandexdisk.api.dto.Error

class ErrorGenerator {
    @Step("Сгенерирована 404 ошибка")
    fun generate404Error() = Error(
        message = "Ресурс не найден.",
        description = "Not Found",
        error = "NotFoundError"
    )

    @Step("Сгенерирована 400 ошибка")
    fun generate400Error(folderName: String) = Error(
        message = "Указанный формат ресурса Диска \"$folderName\" не корректен. Должен начинаться с /.",
        description = "Specified path \"$folderName\" has incorrect format",
        error = "DiskPathFormatError"
    )

    @Step("Сгенерирована 409 ошибка")
    fun generate409Error(folderName: String) = Error(
        message = "Указанного пути \"$folderName\" не существует.",
        description = "Specified path \"$folderName\" doesn't exists.",
        error = "DiskPathDoesntExistsError"
    )
}