package ru.igorshaposhnikov.currents.util

fun formatDate(isoDate: String): String {
    return try {
        val datePart = isoDate.substringBefore("T")
        val parts = datePart.split("-")
        if (parts.size == 3) {
            "${parts[1]}/${parts[2]}/${parts[0]}"
        } else {
            isoDate
        }
    } catch (e: Exception) {
        isoDate
    }
}
