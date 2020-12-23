package com.team3.getjob;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    @Test
    public void isValidPassword_test() {
        assertFalse(Validation.isValidPassword("12345"));
        assertFalse(Validation.isValidPassword("123abc"));
        assertFalse(Validation.isValidPassword("123456789"));
        assertFalse(Validation.isValidPassword("12345a789"));
        assertFalse(Validation.isValidPassword("12345A789"));
        assertTrue(Validation.isValidPassword("ABC123a$$$"));
    }

    @Test
    public void isEmailValid_test() {
        assertFalse(Validation.isEmailValid("alon"));
        assertFalse(Validation.isEmailValid("alon@alon"));
        assertTrue(Validation.isEmailValid("alon@alon.com"));
    }

    @Test
    public void isAlpha_test() {
        assertTrue(Validation.isAlpha("alon"));
        assertFalse(Validation.isAlpha("al0n"));
        assertFalse(Validation.isAlpha("123"));
    }

    @Test
    public void isValidPhoneNumber_test() {
        assertFalse(Validation.isValidPhoneNumber("123456789"));
        assertTrue(Validation.isValidPhoneNumber("0523567122"));
    }

    @Test
    public void isValidId_test() {
        assertFalse(Validation.isValidId("12345678a"));
        assertFalse(Validation.isValidId("1234"));
        assertTrue(Validation.isValidId("123456789"));
    }

    @Test
    public void isValidAge_test() {
        assertFalse(Validation.isValidAge("123"));
        assertFalse(Validation.isValidAge("1"));
        assertFalse(Validation.isValidAge("01"));
        assertTrue(Validation.isValidAge("21"));
    }

    @Test
    public void isValidCompany() {
        assertFalse(Validation.isValidCompany(""));
        assertTrue(Validation.isValidCompany("text"));

    }




}