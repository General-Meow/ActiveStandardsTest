package com.paulhoang.services;

import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by paul on 01/07/16.
 **/
public class DateService {

    private static final int LOWEST_VALID_YEAR = 1901;
    private static final int HIGHEST_VALID_YEAR = 2999;
    private static DateService INSTANCE;

    private DateService() {

    }

    public static DateService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DateService();
        }
        return INSTANCE;
    }

    //calculate days between dates excluding the dates provided
    public Long calculateDaysBetweenDates(final LocalDate firstDate, final LocalDate secondDate) {
        final long daysBetween = ChronoUnit.DAYS.between(firstDate, secondDate);
        return daysBetween > 0 ? daysBetween - 1 : 0;
    }

    public LocalDate convertToDate(final List<Integer> dateNumbers) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(dateNumbers));
        Preconditions.checkArgument(isValidDate(dateNumbers));

        return LocalDate.of(dateNumbers.get(2), dateNumbers.get(0), dateNumbers.get(1));
    }

    protected boolean isValidDate(final List<Integer> dateNumbers) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(dateNumbers));

        try {
            final LocalDate convertedDate = LocalDate.of(dateNumbers.get(2), dateNumbers.get(0), dateNumbers.get(1));
            return (convertedDate.getYear() >= LOWEST_VALID_YEAR && convertedDate.getYear() <= HIGHEST_VALID_YEAR);
        } catch (final DateTimeException dte) {
            return false;
        }
    }


}
