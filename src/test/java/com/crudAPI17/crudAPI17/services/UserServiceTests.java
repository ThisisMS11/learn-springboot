package com.crudAPI17.crudAPI17.services;
import com.crudAPI17.crudAPI17.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserName(){
        assertEquals(4, 2+2);
        assertNotNull(userRepository.findByUserName("ram2"));
    }
}
