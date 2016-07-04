package com.paulhoang.services;

import com.paulhoang.services.InputService;
import com.paulhoang.utils.InputWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by paul on 02/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class InputServiceTest {

    private static final String VALID_CONSOLE_INPUT = "1, 2, 3, 4, 5, 6";
    private static final String INVALID_CONSOLE_INPUT_CONTAINS_CHAR = "3, 2, 1900, EXX, 2, 1922";

    @Spy
    @InjectMocks
    private InputService testObj = InputService.getInstance();

    @Mock
    private InputWrapper inputWrapperMock;

    @Test
    public void getDateInputShouldCallConsoleInputWrapperAndValidateInput() {
        when(inputWrapperMock.readInputFromConsole()).thenReturn(VALID_CONSOLE_INPUT);

        testObj.getDateInput();

        verify(inputWrapperMock).readInputFromConsole();
        verify(testObj).validateInput(VALID_CONSOLE_INPUT);
        verify(testObj).splitToDateList(VALID_CONSOLE_INPUT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDateInputShouldThrowExceptionWhenInvalidateInput() {
        when(inputWrapperMock.readInputFromConsole()).thenReturn(INVALID_CONSOLE_INPUT_CONTAINS_CHAR);

        try {
            testObj.getDateInput();
        } finally {
            verify(inputWrapperMock).readInputFromConsole();
            verify(testObj).validateInput(INVALID_CONSOLE_INPUT_CONTAINS_CHAR);
            verify(testObj, never()).splitToDateList(anyString());
        }

    }

    @Test
    public void splitToDateListShouldReturnListOfIntegersWhenValid() {
        when(inputWrapperMock.readInputFromConsole()).thenReturn(VALID_CONSOLE_INPUT);

        final List<Integer> result = testObj.splitToDateList(VALID_CONSOLE_INPUT);

        Assert.assertEquals(6, result.size());
        Assert.assertTrue(1 == result.get(0));
        Assert.assertTrue(2 == result.get(1));
        Assert.assertTrue(3 == result.get(2));
        Assert.assertTrue(4 == result.get(3));
        Assert.assertTrue(5 == result.get(4));
        Assert.assertTrue(6 == result.get(5));
    }
}