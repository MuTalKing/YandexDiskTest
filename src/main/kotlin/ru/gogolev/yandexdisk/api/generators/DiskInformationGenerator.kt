package ru.gogolev.yandexdisk.api.generators

import io.qameta.allure.Step
import ru.gogolev.yandexdisk.api.dto.DiskInformation

class DiskInformationGenerator {
    @Step("Создание информации о диске для пользователя GembelSer")
    fun createDiskInformationForGembelSerUser() = DiskInformation(
        is_paid = false,
        max_file_size = 1073741824,
        paid_max_file_size = 53687091200,
        reg_time = "2023-08-14T17:43:19+00:00",
        revision = 1692035003618157,
        system_folders = DiskInformation.SystemFolders(
            applications = "disk:/Приложения",
            attach = "disk:/Почтовые вложения",
            calendar = "disk:/Материалы встреч",
            downloads = "disk:/Загрузки/",
            facebook = "disk:/Социальные сети/Facebook",
            google = "disk:/Социальные сети/Google+",
            instagram = "disk:/Социальные сети/Instagram",
            mailru = "disk:/Социальные сети/Мой Мир",
            messenger = "disk:/Файлы Мессенджера",
            odnoklassniki = "disk:/Социальные сети/Одноклассники",
            photostream = "disk:/Фотокамера/",
            scans = "disk:/Сканы",
            screenshots = "disk:/Скриншоты/",
            social = "disk:/Социальные сети/",
            vkontakte = "disk:/Социальные сети/ВКонтакте"
        ),
        total_space = 5368709120,
        trash_size = 0,
        unlimited_autoupload_enabled = false,
        used_space = 40821195,
        user = DiskInformation.User(
            country = "ru",
            display_name = "GembelSer",
            is_child = false,
            login = "GembelSer",
            reg_time = "2023-08-14T17:43:19+00:00",
            uid = "1880244619"
        )
    )
}