package com.illogicalparadox.illogicalParadox.service;

import com.illogicalparadox.illogicalParadox.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;
    @Disabled
    @Test
    public void testFindByUserName(){
        assertEquals(4,2+2);
        assertNotNull(userRepository.findByUsername("vidit"));
    }
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "100,100,200"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected,a+b);
    }
}
