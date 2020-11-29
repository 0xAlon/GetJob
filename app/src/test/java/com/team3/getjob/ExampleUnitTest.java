package com.team3.getjob;

import org.junit.Test;

import static org.junit.Assert.*;
import com.team3.getjob.RegisterUser;

public class ExampleUnitTest {

    @Test
    public void isValidPassword_test() {
        assertFalse(RegisterUser.isValidPassword("12345"));
        assertFalse(RegisterUser.isValidPassword("123abc"));
    }

    public void isEmailValid_test() {
        assertFalse(RegisterUser.isEmailValid("alon"));
        assertFalse(RegisterUser.isEmailValid("alon@alon"));
        assertTrue(RegisterUser.isEmailValid("alon@alon.com"));
    }


}