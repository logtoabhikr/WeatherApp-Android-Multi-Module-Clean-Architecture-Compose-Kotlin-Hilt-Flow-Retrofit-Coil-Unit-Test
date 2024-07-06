package com.lbg.domain.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class ExtKtTest {
    @Test
    fun `parseDateToDay() correctly parses a date string into a day name`() {
        val expectedDayName = "Sun, 09 Apr"
        val actualDayName = "2023-04-09".parseDateTo(
            Constants.INPUT_DATE_TO_DAY_FORMAT,
            Constants.OUTPUT_DAY_FORMAT
        )
        assertEquals(expectedDayName, actualDayName)
    }

    @Test
    fun `parseDateToDayError() returns the original date string if the date string is invalid`() {

        val dateString = "Invalid date string"

        val actualDayName = dateString.parseDateTo(
            Constants.INPUT_DATE_TO_DAY_FORMAT,
            Constants.OUTPUT_DAY_FORMAT
        )

        assertEquals(dateString, actualDayName)
    }

    @Test
    fun `parseDateToTime() correctly parses a date string into a time string`() {
        val expectedTimeString = "12:00 am"
        val actualTimeString = "2023-04-09 12:00".parseDateTo(
            Constants.INPUT_DATE_TO_TIME_FORMAT,
            Constants.OUTPUT_TIME_FORMAT
        )
        assertEquals(expectedTimeString, actualTimeString)
    }

    @Test
    fun `parseDateToTimeError() returns the original date string if the date string is invalid`() {

        val dateString = "Invalid date string"

        val actualDayName = dateString.parseDateTo(
            Constants.INPUT_DATE_TO_TIME_FORMAT,
            Constants.OUTPUT_TIME_FORMAT
        )

        assertEquals(dateString, actualDayName)
    }
}