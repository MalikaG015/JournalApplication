package com.journalApp.demo.service;


import com.journalApp.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserName(){
        assertNotNull(userRepository.findByUserName("Ram"));
    }

    @ParameterizedTest
    @CsvSource({
            "2,1,3",
            "2,10,12"
    })
    public void test(int a, int b,int expected){
        assertEquals(expected,a+b);

    }
}
