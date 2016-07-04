package com.paulhoang.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Created by paul on 02/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DateServiceTest {

    private static final int EXPECTED_DAY = 28;
    private static final int EXPECTED_MONTH = 12;
    private static final int EXPECTED_YEAR = 1999;

    @Spy
    private DateService testObj = DateService.getInstance();

    private List<Integer> validDateInput;
    private List<Integer> invalidDateInput;
    private List<Integer> invalidEmptyDateInput;
    private List<Integer> invalidYearDateInputUpper, invalidYearDateInputLower;
    private LocalDate validLocalDate, validLocalDateTomorrow, validLocalDate2DaysAhead;


    @Before
    public void setup() {
        validDateInput = new ArrayList<>();
        validDateInput.add(12);
        validDateInput.add(28);
        validDateInput.add(1999);

        invalidDateInput = new ArrayList<>();
        invalidDateInput.add(9999);
        invalidDateInput.add(3333);
        invalidDateInput.add(1999);

        invalidEmptyDateInput = new ArrayList<>();

        invalidYearDateInputUpper = new ArrayList<>();
        invalidYearDateInputUpper.add(12);
        invalidYearDateInputUpper.add(28);
        invalidYearDateInputUpper.add(3001);

        invalidYearDateInputLower = new ArrayList<>();
        invalidYearDateInputLower.add(12);
        invalidYearDateInputLower.add(28);
        invalidYearDateInputLower.add(1800);

        validLocalDate = LocalDate.of(1999, 12, 20);
        validLocalDateTomorrow = LocalDate.of(1999, 12, 21);
        validLocalDate2DaysAhead = LocalDate.of(1999, 12, 22);
    }

    @Test
    public void isValidDateShouldReturnTrueIfInputIsValid() {
        final boolean result = testObj.isValidDate(validDateInput);
        Assert.assertTrue(result);
    }

    @Test
    public void isValidDateShouldReturnFalseIfInputIsInvalid() {
        final boolean result = testObj.isValidDate(invalidDateInput);
        Assert.assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidDateShouldThrowExceptionIfInputIsEmpty() {
        testObj.isValidDate(invalidEmptyDateInput);
    }

    @Test
    public void isValidDateShouldReturnFalseIfYearIsBefore1901() {
        final boolean result = testObj.isValidDate(invalidYearDateInputLower);
        Assert.assertFalse(result);
    }

    @Test
    public void isValidDateShouldReturnFalseIfYearIsAfter2999() {
        final boolean result = testObj.isValidDate(invalidYearDateInputUpper);
        Assert.assertFalse(result);
    }

    @Test
    public void convertToDateShouldReturnDateIfInputIsValid() {
        final LocalDate result = testObj.convertToDate(validDateInput);

        verify(testObj).isValidDate(validDateInput);

        Assert.assertEquals(EXPECTED_DAY, result.getDayOfMonth());
        Assert.assertEquals(EXPECTED_MONTH, result.getMonthValue());
        Assert.assertEquals(EXPECTED_YEAR, result.getYear());
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertToDateShouldThrowExceptionIfInputIsInvalid() {
        try {
            testObj.convertToDate(invalidDateInput);
        } finally {
            verify(testObj).isValidDate(invalidDateInput);
        }
    }

    @Test
    public void calculateDaysBetweenDatesShouldReturnDayBetween2DatesExcludingTheDatesProvided_1() {
        final Long result = testObj.calculateDaysBetweenDates(validLocalDate, validLocalDateTomorrow);

        Assert.assertEquals(Long.valueOf(0), result);
    }

    @Test
    public void calculateDaysBetweenDatesShouldReturnDayBetween2DatesExcludingTheDatesProvided_2() {
        final Long result = testObj.calculateDaysBetweenDates(validLocalDate, validLocalDate2DaysAhead);

        Assert.assertEquals(Long.valueOf(1), result);
    }

    @Test
    public void calculateDaysBetweenDatesShouldReturnDayBetween2DatesEvenDatesAreFlipped() {
        final Long result = testObj.calculateDaysBetweenDates(validLocalDate, validLocalDateTomorrow);

        Assert.assertEquals(Long.valueOf(0), result);
    }
}