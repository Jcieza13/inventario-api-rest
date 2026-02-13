package com.inventory.application.service.Integration;

import com.inventory.application.mapper.CategoryMapper;
import com.inventory.infrastucture.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class ContextLoadTest {



    @Test
    void contextLoads() {
    }
}
