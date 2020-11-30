package com.team3.getjob;

import org.junit.Test;

import static org.junit.Assert.*;
import com.team3.getjob.RegisterUser;

public class ExampleUnitTest {

    @Test
    public void isValidPassword_test() {
        assertFalse(RegisterUser.isValidPassword("12345"));
        assertFalse(RegisterUser.isValidPassword("123abc"));
        assertFalse(RegisterUser.isValidPassword("123456789"));
        assertFalse(RegisterUser.isValidPassword("12345a789"));
        assertFalse(RegisterUser.isValidPassword("12345A789"));
        assertTrue(RegisterUser.isValidPassword("ABC123a$$$"));
    }

    @Test
    public void isEmailValid_test() {
        assertFalse(RegisterUser.isEmailValid("alon"));
        assertFalse(RegisterUser.isEmailValid("alon@alon"));
        assertTrue(RegisterUser.isEmailValid("alon@alon.com"));
    }

    @Test
    public void isAlpha_test() {
        assertTrue(RegisterUser.isAlpha("alon"));
        assertFalse(RegisterUser.isAlpha("al0n"));
        assertFalse(RegisterUser.isAlpha("123"));
    }

    @Test
    public void isValidPhoneNumber_test() {
        assertFalse(RegisterUser.isValidPhoneNumber("123456789"));
        assertTrue(RegisterUser.isValidPhoneNumber("0523567122"));
    }

    @Test
    public void isValidId_test() {
        assertFalse(RegisterUser.isValidId("12345678a"));
        assertFalse(RegisterUser.isValidId("1234"));
        assertTrue(RegisterUser.isValidId("123456789"));
    }





}