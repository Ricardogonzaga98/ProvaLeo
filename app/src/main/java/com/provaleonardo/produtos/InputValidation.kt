package com.provaleonardo.produtos

object InputValidation {

    private val alphanumericCode = Regex("^[A-Za-z0-9]+$")
    private val pricePattern = Regex("^\\d+(\\.\\d{1,2})?$")

    fun normalizeDecimalInput(raw: String): String = raw.trim().replace(',', '.')

    fun isNonBlank(s: String): Boolean = s.isNotBlank()

    fun isValidProductCode(code: String): Boolean =
        code.isNotBlank() && alphanumericCode.matches(code.trim())

    fun isValidPrice(raw: String): Boolean {
        val normalized = normalizeDecimalInput(raw)
        if (!pricePattern.matches(normalized)) return false
        val value = normalized.toDoubleOrNull() ?: return false
        return value > 0
    }

    fun parsePrice(raw: String): Double = normalizeDecimalInput(raw).toDouble()

    fun isValidQuantity(raw: String): Boolean {
        val trimmed = raw.trim()
        if (trimmed.isEmpty()) return false
        val value = trimmed.toIntOrNull() ?: return false
        return value > 0
    }

    fun parseQuantity(raw: String): Int = raw.trim().toInt()
}
