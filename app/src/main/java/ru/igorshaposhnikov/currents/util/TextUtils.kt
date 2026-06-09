package ru.igorshaposhnikov.currents.util

fun String.truncate(maxLength: Int): String {
    return if (this.length <= maxLength) this else this.take(maxLength - 3) + "..."
}
