package com.lgzarturo.api.personal;

import com.lgzarturo.api.personal.api.auth.AuthController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PersonalApplicationTests {

    @Autowired
    private AuthController authController;

	@Test
	void contextLoads() {
        Assertions.assertNotNull(authController);
	}

}
