package org.chobit.calf.service;

import org.chobit.calf.TestBase;
import org.chobit.calf.utils.MD5;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author robin
 */
public class UserServiceTest extends TestBase {


    @Value("${calf.passoword-salt}")
    private String passwordSalt;

    @Test
    public void encode() {
        System.out.println(MD5.encode(passwordSalt, "admin"));
    }


}
