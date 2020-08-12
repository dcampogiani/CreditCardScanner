package com.danielecampogiani.creditcardscanner

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ExtractorTest {

    @Test
    fun maestro() {
        val input = "O0a some random text\n" +
                "BANCOMAT\n" +
                ")\n" +
                "7391 8492 8502 752 495\n" +
                "VALID\n" +
                "09/27\n" +
                "debit\n" +
                "62846284\n" +
                "maestro"

        val expected = CardDetails(
            number = "7391 8492 8502 752 495",
            expirationMonth = "09",
            expirationYear = "27"
        )

        val result = Extractor.extractData(input)

        assertEquals(expected, result)
    }

    @Test
    fun visa() {
        val input = "7194 9274 7591 7593\n" +
                ")\n" +
                "08/23 EXP 777 CE\n" +
                "website.com DEBIT TAG SYSTEMS 72945-08-27"

        val expected = CardDetails(
            number = "7194 9274 7591 7593",
            expirationMonth = "08",
            expirationYear = "23"
        )

        val result = Extractor.extractData(input)

        assertEquals(expected, result)
    }
}