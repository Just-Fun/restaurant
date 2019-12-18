package com.serzh.aop.limitation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AOPTestConfiguration.class})
public class ParamHandlerChildrenTest {

    @Autowired
    ParamHandlerChildren classUnderTest;

    @Test
    public void failure_aspect_cause_override_aspect() {
        String result = classUnderTest.handle("some");
        assertSame("some", result);
    }

}