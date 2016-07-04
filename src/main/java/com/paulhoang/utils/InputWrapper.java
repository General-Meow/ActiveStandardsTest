package com.paulhoang.utils;

import java.util.Scanner;

/**
 * Created by paul on 02/07/16.
 * <p>
 * Class created to just wrap the java console input.
 * Only done as I want to mock the console input for tests.
 */
public class InputWrapper {

    public String readInputFromConsole() {
        return new Scanner(System.in).nextLine();
    }
}
