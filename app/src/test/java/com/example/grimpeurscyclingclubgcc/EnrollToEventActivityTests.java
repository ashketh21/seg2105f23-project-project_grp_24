package com.example.grimpeurscyclingclubgcc;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EnrollToEventActivityTests {
    private EnrollToEventActivity enrollToEventActivity;

    //Initial set up before tests
    @Before
    public void setUp() {
        enrollToEventActivity = new EnrollToEventActivity();
    }
    @Test
    //Test to see if an entered age interacts properly with the code
    public void testEnrollWithValidAge() {
        enrollToEventActivity.setEnteredAge("25");
        boolean result = enrollToEventActivity.validateAge();
        assertTrue(result);
    }

    @Test
    //Test to see if an empty age works
    public void testEnrollWithEmptyAge() {
        enrollToEventActivity.setEnteredAge("");
        boolean result = enrollToEventActivity.validateAge();
        assertFalse(result);
    }

    @Test
    //Test to see if an age thats too small works
    public void testEnrollWithSmallNumber() {
        enrollToEventActivity.setEnteredAge("15");
        boolean result = enrollToEventActivity.validateAge();
        assertFalse(result);
    }

    @Test
    //Test to see if an invalid age input works
    public void testEnrollWithInvalidInput() {
        enrollToEventActivity.setEnteredAge("asjlj");
        boolean result = enrollToEventActivity.validateAge();
        assertFalse(result);
    }



}
