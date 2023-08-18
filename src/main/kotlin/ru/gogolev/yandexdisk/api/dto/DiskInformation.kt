package ru.gogolev.yandexdisk.api.dto

data class DiskInformation(
    val is_paid: Boolean,
    val max_file_size: Int,
    val paid_max_file_size: Long,
    val reg_time: String,
    val revision: Long,
    val system_folders: SystemFolders,
    val total_space: Long,
    val trash_size: Int,
    val unlimited_autoupload_enabled: Boolean,
    val used_space: Int,
    val user: User
) {
    data class SystemFolders(
        val applications: String,
        val attach: String,
        val calendar: String,
        val downloads: String,
        val facebook: String,
        val google: String,
        val instagram: String,
        val mailru: String,
        val messenger: String,
        val odnoklassniki: String,
        val photostream: String,
        val scans: String,
        val screenshots: String,
        val social: String,
        val vkontakte: String
    )

    data class User(
        val country: String,
        val display_name: String,
        val is_child: Boolean,
        val login: String,
        val reg_time: String,
        val uid: String
    )
}