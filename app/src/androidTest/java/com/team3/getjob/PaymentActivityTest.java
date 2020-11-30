package com.team3.getjob;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PaymentActivityTest {

    @Rule
    public ActivityTestRule<PaymentActivity> mPaymentTestRule=new ActivityTestRule<PaymentActivity>(PaymentActivity.class);
    private PaymentActivity mPayment=null;//reference fo Filter activity

    @Before
    public void setUp() throws Exception {
        mPayment=mPaymentTestRule.getActivity();
    }

    @Test
    public void  testLaunch(){
        View view = mPayment.findViewById(R.id.textView);

        assertNotNull(view);
    }
    @Test
    public void CheckInputTest(){
        PaymentActivity Pact=mPaymentTestRule.getActivity();
        assertTrue(Pact.CheckInput("123456"));
        assertFalse(Pact.CheckInput("1234z231z23@"));
    }

    @After
    public void tearDown() throws Exception {
        mPayment=null;
    }


}