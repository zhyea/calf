package org.chobit.calf.service;

import org.chobit.calf.TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MyServiceTest extends TestBase {

    @Autowired
    private MyService myService;

    @Test
    public void getStr(){
        System.out.println(myService.getStr());
        System.out.println(myService.getStr());
        System.out.println(myService.getStr());
        System.out.println(myService.getStr());
        System.out.println(myService.getStr());
        System.out.println(myService.getStr());
    }

}
