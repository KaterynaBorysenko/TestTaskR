package com.example.testtaskr.utils
import java.util.*
import java.util.concurrent.TimeUnit

val ONE_HOUR_MS = TimeUnit.MINUTES.toMillis(60)
val ONE_DAY_MS = TimeUnit.HOURS.toMillis(24)

fun formatDate(createdTime: Long): String {
    val currentDate = Calendar.getInstance(TimeZone.getTimeZone("UTC")).time.time

    return when (val timeAgo: Long = currentDate - (createdTime * 1000)) {
        in 0..ONE_HOUR_MS -> {
            String.format(
                "%d minutes ago",
                TimeUnit.MILLISECONDS.toMinutes(timeAgo)
            )
        }
        in (ONE_HOUR_MS + 1L)..ONE_DAY_MS -> {
            String.format(
                "%d hours ago",
                TimeUnit.MILLISECONDS.toHours(timeAgo)
            )
        }
        else -> {
            String.format(
                "%d days ago",
                TimeUnit.MILLISECONDS.toDays(timeAgo)
            )
        }
    }
}

class Utils{
    companion object{
        var nowIsSawing = false

    }
}