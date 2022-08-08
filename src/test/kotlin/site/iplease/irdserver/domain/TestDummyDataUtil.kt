package site.iplease.irdserver.domain

import java.time.LocalDate
import kotlin.random.Random

object TestDummyDataUtil {
    fun id() = Random.nextLong(1, Long.MAX_VALUE)
    fun randomDate(): LocalDate {
        val minDay = LocalDate.MIN.toEpochDay()
        val maxDay = LocalDate.MAX.toEpochDay()
        val randomDay: Long = Random.nextLong(minDay, maxDay)
        return LocalDate.ofEpochDay(randomDay)
    }
}