package com.automotive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Basic smoke test – verifies the Spring application context loads correctly.
 * Run with: mvn test
 *
 * NOTE: Requires a running PostgreSQL instance OR a test profile
 * with an in-memory database configured.
 */
@SpringBootTest
class AutomotiveAnalyticsApplicationTests {

    @Test
    void contextLoads() {
        // If this test passes, the application context started without errors.
        // All beans were created and wired correctly.
    }
}
