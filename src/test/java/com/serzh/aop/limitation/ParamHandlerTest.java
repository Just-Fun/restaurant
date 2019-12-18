package com.serzh.aop.limitation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AOPTestConfiguration.class})
public class ParamHandlerTest {

    @Autowired
    ParamHandler classUnderTest;

    @Test
    public void success_handle() {
        String result = classUnderTest.handle("some");
        assertTrue(result.contains("AndParam"));
        assertEquals("someAndParam", result);
    }

    @Test
    public void failure_aspect_cause_delegate_method_without_aspect() {
        String result = classUnderTest.handle2("some");
        assertSame("some", result);
    }

    @Test
    public void failure_aspect_cause_aspect_using_aspect() {
        String result = classUnderTest.handle3("some");
        assertNotSame(result, "someAndParamAndParam");
    }


}