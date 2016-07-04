package com.paulhoang;

import com.paulhoang.services.DateService;
import com.paulhoang.services.InputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by paul on 01/07/16.
 */
public class QuizRunner {

    protected final static int LOOP_COUNT = 6;
    private static final Logger LOG = LoggerFactory.getLogger(QuizRunner.class);

    private DateService dateService;
    private InputService inputService;

    public QuizRunner() {
        //old school - no injection
        dateService = DateService.getInstance();
        inputService = InputService.getInstance();
    }

    public static void main(final String[] args) {

        final QuizRunner quizRunner = new QuizRunner();
        quizRunner.loopTillEnd();

    }

    protected void loopTillEnd() {

        for (int i = 0; i < LOOP_COUNT; i++) {
            getDaysBetweenDates();
        }
    }

    protected void getDaysBetweenDates() {
        try {
            LOG.info("Input line:");
            final List<Integer> dates = inputService.getDateInput();
            final LocalDate firstDate = dateService.convertToDate(dates.subList(0, 3));
            final LocalDate secondDate = dateService.convertToDate(dates.subList(3, 6));

            final Long daysBetween = dateService.calculateDaysBetweenDates(firstDate, secondDate);
            LOG.info("Output : " + daysBetween);
        } catch (final Exception e) {
            LOG.warn("Input exception please try again");
        }
    }

}
