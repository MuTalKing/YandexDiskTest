package ru.gogolev.yandexdisk.api.assertions

import io.kotest.assertions.asClue
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.qameta.allure.Step
import ru.gogolev.yandexdisk.api.dto.DiskInformation

class DiskInformationAssertion {
    @Step("Проверка совпадения информации о диске, полученной от сервиса, с ожидаемой информацией")
    fun check(actualDiskInformation: DiskInformation, expectedDiskInformation: DiskInformation) {
        assertSoftly {
            actualDiskInformation.asClue { actualDiskInformation ->
                actualDiskInformation.max_file_size shouldBe expectedDiskInformation.max_file_size
                actualDiskInformation.paid_max_file_size shouldBe expectedDiskInformation.paid_max_file_size
                actualDiskInformation.total_space shouldBe expectedDiskInformation.total_space
                actualDiskInformation.reg_time shouldBe expectedDiskInformation.reg_time
                actualDiskInformation.trash_size shouldBe expectedDiskInformation.trash_size
                actualDiskInformation.is_paid shouldBe expectedDiskInformation.is_paid
                actualDiskInformation.used_space shouldBe expectedDiskInformation.used_space
                actualDiskInformation.system_folders.asClue { systemFolders ->
                    systemFolders.odnoklassniki shouldBe expectedDiskInformation.system_folders.odnoklassniki
                    systemFolders.google shouldBe expectedDiskInformation.system_folders.google
                    systemFolders.instagram shouldBe expectedDiskInformation.system_folders.instagram
                    systemFolders.vkontakte shouldBe expectedDiskInformation.system_folders.vkontakte
                    systemFolders.attach shouldBe expectedDiskInformation.system_folders.attach
                    systemFolders.mailru shouldBe expectedDiskInformation.system_folders.mailru
                    systemFolders.downloads shouldBe expectedDiskInformation.system_folders.downloads
                    systemFolders.applications shouldBe expectedDiskInformation.system_folders.applications
                    systemFolders.facebook shouldBe expectedDiskInformation.system_folders.facebook
                    systemFolders.social shouldBe expectedDiskInformation.system_folders.social
                    systemFolders.messenger shouldBe expectedDiskInformation.system_folders.messenger
                    systemFolders.calendar shouldBe expectedDiskInformation.system_folders.calendar
                    systemFolders.scans shouldBe expectedDiskInformation.system_folders.scans
                    systemFolders.screenshots shouldBe expectedDiskInformation.system_folders.screenshots
                    systemFolders.photostream shouldBe expectedDiskInformation.system_folders.photostream
                }
                actualDiskInformation.user.asClue { actualUser ->
                    actualUser.reg_time shouldBe expectedDiskInformation.user.reg_time
                    actualUser.display_name shouldBe expectedDiskInformation.user.display_name
                    actualUser.uid shouldBe expectedDiskInformation.user.uid
                    actualUser.country shouldBe expectedDiskInformation.user.country
                    actualUser.is_child shouldBe expectedDiskInformation.user.is_child
                    actualUser.login shouldBe expectedDiskInformation.user.login

                }
                actualDiskInformation.unlimited_autoupload_enabled shouldBe expectedDiskInformation.unlimited_autoupload_enabled
                actualDiskInformation.revision shouldBe expectedDiskInformation.revision
            }
        }
    }
}