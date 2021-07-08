package com.unatxe.commons.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone

private val time2001 = 978307200000L

fun DateTime.getSince2001() : Long{
    return DateTime.now().millis - time2001
}

fun DateTime.convertTo2001Date(time: Long): Long? {
    val dt = DateTime("2001-01-01T00:00:00.000-00:00")
    val time2001milliseconds = dt.millis
    val newDt = DateTime(time - time2001milliseconds)
    return newDt.millis / 1000
}

fun DateTime.getCurrentSecondsWithUTC(): Long? {
    val dateTime = DateTime.now(DateTimeZone.UTC)

    return dateTime.millis / 1000
}