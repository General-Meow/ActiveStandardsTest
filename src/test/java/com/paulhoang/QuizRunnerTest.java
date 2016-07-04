package com.paulhoang;

import com.paulhoang.services.DateService;
import com.paulhoang.utils.InputUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by paul on 01/07/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class QuizRunnerTest {

    private static final List<Integer> VALID_DATE_LIST = Arrays.asList(1, 1, 1999, 1, 5, 1999);
    private static final LocalDate FIRST_DATE = LocalDate.of(1999, 1, 1);
    private static final LocalDate SECOND_DATE = LocalDate.of(1999, 5, 1);

    @Spy
    @InjectMocks
    private QuizRunner testObj = new QuizRunner();

    @Mock
    private DateService dateServiceMock;
    @Mock
    private InputUtil inputUtilMock;
    @Captor
    private ArgumentCaptor<List<Integer>> dateCaptor;

    @Test
    public void loopTillEndShouldCallGetDaysBetweenDates6Times() {
        testObj.loopTillEnd();

        verify(testObj, times(QuizRunner.LOOP_COUNT)).getDaysBetweenDates();
    }

    @Test
    public void getDaysBetweenDatesShouldGetInputFromInputUtils() {
        testObj.getDaysBetweenDates();
        verify(inputUtilMock).getDateInput();
    }

    @Test
    public void getDaysBetweenDatesShouldConvertDatesFromInput() {
        when(inputUtilMock.getDateInput()).thenReturn(VALID_DATE_LIST);
        testObj.getDaysBetweenDates();

        verify(dateServiceMock, times(2)).convertToDate(dateCaptor.capture());

        final List<List<Integer>> allDates = dateCaptor.getAllValues();

        Assert.assertTrue(allDates.get(0).equals(Arrays.asList(1, 1, 1999)));
        Assert.assertTrue(allDates.get(1).equals(Arrays.asList(1, 5, 1999)));
    }

    @Test
    public void getDaysBetweenDatesShouldCallCalculateBetweenDatesWithOutputFromConvertDate() {
        when(inputUtilMock.getDateInput()).thenReturn(VALID_DATE_LIST);
        when(dateServiceMock.convertToDate(anyList()))
                .thenReturn(FIRST_DATE)
                .thenReturn(SECOND_DATE);

        testObj.getDaysBetweenDates();

        verify(dateServiceMock).calculateDaysBetweenDates(FIRST_DATE, SECOND_DATE);
    }

}
