package com.paulhoang.utils;

import java.util.Scanner;

/**
 * Created by paul on 02/07/16.
 *
 * Class created to just wrap the java console static method.
 * Only done as I want to mock the console output for tests.
 *
 * restricted to default on purpose
 */
class InputWrapper {

    protected String readInputFromConsole(){
        return new Scanner(System.in).nextLine();
    }
}
