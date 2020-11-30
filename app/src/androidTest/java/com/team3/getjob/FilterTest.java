package com.team3.getjob;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class FilterTest {

    @Rule
    public ActivityTestRule<Filter> mFilterTestRule=new ActivityTestRule<Filter>(Filter.class);//Filter activity is Launched
    private Filter mFilter=null;//reference fo Filter activity

    @Before
    public void setUp() throws Exception {
        mFilter=mFilterTestRule.getActivity();
    }
    //test will be executed

    @Test
    public void  testLaunch(){
        View view = mFilter.findViewById(R.id.textView);

       assertNotNull(view);
    }


    @After
    public void tearDown() throws Exception {
        mFilter=null;
    }
}