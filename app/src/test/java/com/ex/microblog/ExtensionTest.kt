package com.ex.microblog

import com.ex.microblog.core.convertDateToGivenFormat
import com.ex.microblog.core.getDateFromDateConstructor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.text.ParseException
import java.util.*

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */

/**
 * Test extended functions
 */
class ExtensionTest {

    @Test
    fun shouldReturnGivenDateInYearMonthAndDayFormat() {
        val calendar = GregorianCalendar(2017, Calendar.APRIL, 6)
        assertEquals(
            convertDateToGivenFormat(calendar.time, format = "yyyy, MMMM dd"),
            "2017, April 06"
        )
    }

    @Test
    fun shouldFailGivenInvalidDateFormat() {
        val calendar = GregorianCalendar(2017, Calendar.APRIL, 6)
        assertNotEquals(
            convertDateToGivenFormat(calendar.time, format = "MMMMdd,yyyy"),
            "April 06, 2017"
        )
    }

    @Test
    fun testDateIsReturnedWhenDateStringIsSupplied() {

        val dateReturned = getDateFromDateConstructor("April 06, 2017", format = "MMMM dd, yyyy")

        val constructedDate = GregorianCalendar(2017, Calendar.APRIL, 6).time

        assertEquals(dateReturned, constructedDate)
    }


    @Test(expected = ParseException::class)
    fun testDateConstructorFailsWhenDateStringIsSuppliedWithWrongFormat() {
        getDateFromDateConstructor("April 06, 2017", format = "MMMMdd yyyy")
    }

}
