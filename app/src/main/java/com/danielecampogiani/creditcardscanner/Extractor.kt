package com.danielecampogiani.creditcardscanner

object Extractor {

    fun extractData(input: String): CardDetails {
        val lines = input.split("\n")
        val number = extractNumber(lines)
        val (month, year) = extractExpiration(lines)
        return CardDetails(number, month, year)
    }

    private fun extractNumber(lines: List<String>): String? {
        return lines.firstOrNull { line ->
            val subNumbers = line.split(" ")
            subNumbers.isNotEmpty() && subNumbers.flatMap { it.asIterable() }.all { it.isDigit() }
        }
    }

    private fun extractExpiration(lines: List<String>): Pair<String?, String?> {
        val expirationLine = extractExpirationLine(lines)

        val month = expirationLine?.substring(startIndex = 0, endIndex = 2)
        val year = expirationLine?.substring(startIndex = 3, endIndex = 5)
        return Pair(month, year)
    }

    private fun extractExpirationLine(lines: List<String>) =
        lines.flatMap { it.split(" ") }.firstOrNull { it.length == 5 && it[2] == '/' }


}