package com.paulhoang.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.paulhoang.services.DateService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by paul on 01/07/16.
 */
public class InputUtil {

    private static final int REQUIRED_INTEGERS = 6;

    private static InputUtil INSTANCE;
    private InputWrapper inputWrapper;

    private InputUtil() {
        inputWrapper = new InputWrapper();
    }

    public static InputUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InputUtil();
        }

        return INSTANCE;
    }


    public List<Integer> getDateInput() {
        final String consoleInput = inputWrapper.readInputFromConsole();
        if(!validateInput(consoleInput)) {
            throw new IllegalArgumentException("Invalid Input");
        }
        return splitToDateList(consoleInput);
    }

    /**
     * method does a lot of validation, could be made to fail early
     * checks to see if empty
     * checks to see if it has the correct amount of fields
     * checks to see if fields are numbers
     */
    protected boolean validateInput(final String consoleInput) {
        Preconditions.checkArgument(StringUtils.isNotBlank(consoleInput));

        boolean isNotEmpty;
        boolean hasCorrectNumbers;
        boolean allInputInts = true;

        isNotEmpty = !StringUtils.EMPTY.equals(consoleInput);

        final List<String> split = Arrays.asList(StringUtils.split(consoleInput, ','));
        hasCorrectNumbers = split.size() == REQUIRED_INTEGERS;

        for (final String input : split) {
            final String trimmedInput = StringUtils.trim(input);
            allInputInts &= NumberUtils.isNumber(trimmedInput);
        }

        return isNotEmpty && hasCorrectNumbers && allInputInts;
    }

    protected List<Integer> splitToDateList(final String input) {
        final String[] split = StringUtils.split(input, ',');
        final List<Integer> result = Lists.newArrayList();
        for (final String number : split) {
            result.add(NumberUtils.createInteger(StringUtils.trim(number)));
        }

        return result;
    }
}
