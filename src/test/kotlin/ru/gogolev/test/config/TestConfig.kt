package ru.gogolev.test.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.gogolev.yandexdisk.api.assertions.DiskInformationAssertion
import ru.gogolev.yandexdisk.api.assertions.ErrorAssertion
import ru.gogolev.yandexdisk.api.config.RestAssuredConfiguration
import ru.gogolev.yandexdisk.api.generators.DiskInformationGenerator
import ru.gogolev.yandexdisk.api.generators.ErrorGenerator
import ru.gogolev.yandexdisk.api.http.YandexDiskService
import ru.gogolev.yandexdisk.api.utils.YANDEX_DISK_OAUTH_TOKEN
import ru.gogolev.yandexdisk.api.utils.YANDEX_DISK_URL

@Configuration
class TestConfig {

    @Bean
    fun yandexRestAssuredConfiguration() = RestAssuredConfiguration(url = YANDEX_DISK_URL, oAuthToken = YANDEX_DISK_OAUTH_TOKEN)

    @Bean
    fun yandexDiskService(yandexDiskRestAssuredConfiguration: RestAssuredConfiguration) = YandexDiskService(yandexDiskRestAssuredConfiguration)

    @Bean
    fun diskInformationGenerator() = DiskInformationGenerator()

    @Bean
    fun diskInformationAssertion() = DiskInformationAssertion()

    @Bean
    fun errorGenerator() = ErrorGenerator()

    @Bean
    fun errorAssertion() = ErrorAssertion()
}