package org.chobit.calf.spring;

import org.chobit.calf.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomConfigTest extends TestBase {

    @Autowired
    private CustomConfig config;

    @Test
    public void valueOf() {
        System.out.println(config.getDataReserveDates());
        System.out.println(config.getShrinkStartDates());
    }
}
