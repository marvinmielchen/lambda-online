package com.marvinmielchen.lambo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class LamboApplicationTests {

    @Test
    void contextLoads() {
        String source =
                """
                //this is a comment
                ({z y : (y {x : x})} {x : (z x)})
                """;
        Lambo.run(source);
    }

}
