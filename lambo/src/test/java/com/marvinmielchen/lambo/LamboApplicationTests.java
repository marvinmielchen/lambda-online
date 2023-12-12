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
                
                def true (t f) { t };
                
                def false (t f) { f };
                
                def if (c x y) { c x y };
                
                def result () { if true a b };
                
                //def test (a) { (x) {a} (x) {x} };
                """;
        Lambo.run(source);
    }

}
