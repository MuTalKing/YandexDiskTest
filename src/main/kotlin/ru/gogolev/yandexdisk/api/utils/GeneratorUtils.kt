package ru.gogolev.yandexdisk.api.utils

import io.kotest.property.Arb
import io.kotest.property.arbitrary.*

val eng: Arb<Codepoint> = arb(listOf(Codepoint('a'.code))) { rs ->
    Arb.int('a'.code..'z'.code).merge(Arb.int('A'.code..'Z'.code)).values(rs).map { Codepoint(it.value) }}

fun engString(length: Int) = Arb.string(length, eng).next()